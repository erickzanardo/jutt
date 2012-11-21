package com.jutt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.tools.shell.Global;
import org.mozilla.javascript.tools.shell.Main;

public class TestResult {
    private String template;

    private static Context cx;
    private static ScriptableObject scope;

    static {
        cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1);
        cx.setLanguageVersion(Context.VERSION_1_7);

        Global global = Main.getGlobal();
        global.init(cx);

        scope = cx.initStandardObjects();
        Main.processSource(cx, "src/main/resources/com/jutt/js/env.rhino.1.2.js");
        Main.processSource(cx, "src/main/resources/com/jutt/js/jquery-1.8.3.min.js");

    }

    public TestResult(String template) {
        this.template = template;
    }

    public String content(String selector) {

//        Context cx = Context.enter();

//        InputStream in = TestResult.class.getClassLoader().getResourceAsStream("com/jutt/js/jquery-1.8.3.min.js");
//        InputStreamReader reader = new InputStreamReader(in);
//        try {
//            cx.evaluateReader(scope, reader, "<cmd>", 1, null);
//            in.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        StringBuilder js = new StringBuilder();
        js.append("$('").append(template.replaceAll("'", "\\\\'")).append("').find('").append(selector)
                .append(")').text();");

        return (String) cx.evaluateString(scope, js.toString(), "<cmd>", 1, null);
    }
}
