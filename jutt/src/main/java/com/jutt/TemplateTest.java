package com.jutt;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.gson.JsonObject;

public abstract class TemplateTest {

    public abstract File getEngine();

    public abstract File getParser();

    public List<File> additionalFiles() {
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

        List<File> additionalFiles = additionalFiles();
        if (additionalFiles != null) {
            for (File file : additionalFiles) {
                readFile(file, cx, scope);
            }
        }

        StringBuilder function = new StringBuilder();

        function.append("(function() {return templateTestParser('");
        function.append(template.replaceAll("'", "\\\\'")).append("', ");
        function.append(data).append(");})()");

        return (String) cx.evaluateString(scope, function.toString(), "<cmd>",
                1, null);
    }

    private void readFile(File file, Context cx, Scriptable scope) {
        FileReader reader;
        try {
            reader = new FileReader(file);
            cx.evaluateReader(scope, reader, "<cmd>", 1, null);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
