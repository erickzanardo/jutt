package com.jutt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.gson.JsonObject;

public abstract class TemplateTest {

    public abstract URL getEngine();

    public abstract URL getParser();

    public List<URL> additionalFiles() {
        return null;
    }

    public List<String> additionalScripts() {
        return null;
    }

    protected TestResult doTemplateAsResult(String template, JsonObject data, String selector) {
        String templateStr = doTemplateAsString(template, data, selector);
        return new TestResult(templateStr);
    }

    protected String doTemplateAsString(String template, JsonObject data, String selector) {
        Document parse = Jsoup.parse(template);
        Elements select = parse.select(selector);

        if (select != null && select.size() > 0) {
            String text = select.get(0).outerHtml();
            return doTemplateAsString(text, data);
        }

        return null;
    }

    protected TestResult doTemplateAsResult(String template, JsonObject data) {
        String templateStr = doTemplateAsString(template, data);
        return new TestResult(templateStr);
    }

    protected String doTemplateAsString(String template, JsonObject data) {

        Context cx = Context.enter();
        Scriptable scope = cx.initStandardObjects();

        readFile(getEngine(), cx, scope);
        readFile(getParser(), cx, scope);

        List<URL> additionalFiles = additionalFiles();
        if (additionalFiles != null) {
            for (URL file : additionalFiles) {
                readFile(file, cx, scope);
            }
        }

        List<String> additionalScripts = additionalScripts();
        if (additionalScripts != null) {
            for (String string : additionalScripts) {
                cx.evaluateString(scope, string, "<cmd>", 1, null);
            }
        }

        StringBuilder function = new StringBuilder();

        function.append("(function() {return templateTestParser('");
        function.append(template.replaceAll("'", "\\\\'")).append("', ");
        function.append(data).append(");})()");

        return (String) cx.evaluateString(scope, function.toString(), "<cmd>", 1, null);
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
