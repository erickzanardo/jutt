package com.jutt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.gson.JsonObject;

public class TemplateTest {

    private URL engine;
    private URL parser;

    private List<URL> additionalFiles = new ArrayList<URL>();
    private List<String> additionalScripts = new ArrayList<String>();

    public TemplateTest(URL engine, URL parser) {
        super();
        this.engine = engine;
        this.parser = parser;
    }

    public void addAdditionalFile(URL url) {
        additionalFiles.add(url);
    }

    public void addAdditionalScript(String script) {
        additionalScripts.add(script);
    }

    public TestResult doTemplateAsResult(String template, JsonObject data, String selector) {
        String templateStr = doTemplateAsString(template, data, selector);
        return new TestResult(templateStr);
    }

    public String doTemplateAsString(String template, JsonObject data, String selector, MockObject... mockObjects) {
        Document parse = Jsoup.parse(template);
        Elements select = parse.select(selector);

        if (select != null && select.size() > 0) {
            String text = select.get(0).outerHtml();
            return doTemplateAsString(text, data, mockObjects);
        }

        return null;
    }

    public TestResult doTemplateAsResult(String template, JsonObject data, MockObject... mockObjects) {
        String templateStr = doTemplateAsString(template, data, mockObjects);
        return new TestResult(templateStr);
    }

    public String doTemplateAsString(String template, JsonObject data, MockObject... mockObjects) {

        Context cx = Context.enter();
        Scriptable scope = cx.initStandardObjects();

        readFile(engine, cx, scope);
        readFile(parser, cx, scope);

        if (additionalFiles != null) {
            for (URL file : additionalFiles) {
                readFile(file, cx, scope);
            }
        }

        if (additionalScripts != null) {
            for (String string : additionalScripts) {
                cx.evaluateString(scope, string, "<cmd>", 1, null);
            }
        }

        resolveMocks(cx, scope, mockObjects);

        StringBuilder function = new StringBuilder();

        function.append("(function() {return templateTestParser('");
        function.append(template.replaceAll("\n", "").replaceAll("'", "\\\\'")).append("', ");
        function.append(data).append(");})()");

        Object evaluateString = cx.evaluateString(scope, function.toString(), "<cmd>", 1, null);
        if (evaluateString != null) {
            // Can be NativeString
            return evaluateString.toString();
        }
        return null;
    }

    private void resolveMocks(Context cx, Scriptable scope, MockObject[] mockObjects) {
        if (mockObjects.length > 0) {
            StringBuilder mocks = new StringBuilder();

            List<String> initializedObjects = new ArrayList<String>();

            for (MockObject o : mockObjects) {
                String[] path = o.getObject().split("[.]");

                if (path.length > 1) {
                    // Last array position is the object itself
                    for (int i = 0; i < path.length - 1; i++) {
                        String thisObj = "";

                        for (int y = 0; y <= i; y++) {
                            thisObj += path[y];
                            if (y < i) {
                                thisObj += ".";
                            }
                        }

                        if (!initializedObjects.contains(thisObj)) {
                            mocks.append(thisObj).append(" = {} ;");
                            initializedObjects.add(thisObj);
                        }
                    }
                }
                String value = "";
                if (o.getValue() instanceof String) {
                    value = "'" + o.getValue() + "'";
                } else {
                    value = o.getValue().toString();
                }
                
                mocks.append(o.getObject()).append(" = ");
                if (o.isFunction()) {
                    mocks.append("function() {return ").append(value).append(";}; ") ;
                } else {
                    mocks.append(value).append(";");
                }
            }

            cx.evaluateString(scope, mocks.toString(), "<cmd>", 1, null);
        }
    }

    private void readFile(URL url, Context cx, Scriptable scope) {
        InputStreamReader reader = null;
        try {
            InputStream openStream = url.openStream();
            reader = new InputStreamReader(openStream);
            cx.evaluateReader(scope, reader, "<cmd>", 1, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
