package com.jutt;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.gson.JsonObject;

public abstract class TemplateTest {

    public abstract File getEngine();

    public abstract File getParser();

    protected String doTemplate(String template, JsonObject data) {

        try {
            Context cx = Context.enter();
            Scriptable scope = cx.initStandardObjects();

            FileReader reader;
            reader = new FileReader(getEngine());
            cx.evaluateReader(scope, reader, "<cmd>", 1, null);
            reader.close();

            reader = new FileReader(getParser());
            cx.evaluateReader(scope, reader, "<cmd>", 1, null);
            reader.close();

            StringBuilder function = new StringBuilder();

            function.append("(function() {return templateTestParser('");
            function.append(template.replaceAll("'", "\\\\'")).append("', ");
            function.append(data).append(");})()");

            return (String) cx.evaluateString(scope, function.toString(),
                    "<cmd>", 1, null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
