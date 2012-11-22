package com.jutt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.JsonObject;

public class TrimpathScriptFilesTest extends TemplateTest {

    @Override
    public URL getEngine() {
        return Utils.fileToURL(new File("src/main/webapp/js/trimpath-template-1.0.38.js"));
    }

    @Override
    public URL getParser() {
        return Utils.fileToURL(new File("src/main/webapp/js/trimpathParser.js"));
    }

    @Override
    public List<String> additionalScripts() {
        List<String> scripts = new ArrayList<String>();

        StringBuilder function = new StringBuilder();
        function.append("dummyFunction = function (value) {");
        function.append("    return 'Test';");
        function.append("};");
        scripts.add(function.toString());

        return scripts;
    }

    @Test
    public void testResult() {
        JsonObject data = new JsonObject();
        data.addProperty("title", "My name is");
        data.addProperty("x", "Bond,");
        data.addProperty("y", "James Bond");

        String template = Utils.readFile("src/main/webapp/template/trimpath/dummy-template-6.html");
        TestResult result = doTemplateAsResult(template, data);
        assertEquals("Test", result.content("h1"));
        assertEquals("Test Test", result.content(".a"));
        assertEquals("Test", result.content(".a a"));
    }
}
