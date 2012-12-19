package com.jutt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;

public class DoTAdditionalScriptTest {

    private static TemplateTest helper;

    @BeforeClass
    public static void setup() {
        URL engine = Utils.fileToURL(new File("src/main/webapp/js/doT.js"));
        URL parser = Utils.fileToURL(new File("src/main/webapp/js/doTParser.js"));
        helper = new TemplateTest(engine, parser);

        StringBuilder function = new StringBuilder();
        function.append("dummyFunction = function (value) {");
        function.append("    return 'Test';");
        function.append("};");
        helper.addAdditionalScript(function.toString());
    }

    @Test
    public void testResult() {
        JsonObject data = new JsonObject();
        data.addProperty("title", "My name is");
        data.addProperty("x", "Bond,");
        data.addProperty("y", "James Bond");

        String template = Utils.readFile("src/main/webapp/template/dot/dummy-template-6.html");
        TestResult result = helper.doTemplateAsResult(template, data);
        assertEquals("Test", result.content("h1"));
        assertEquals("Test Test", result.content(".a"));
        assertEquals("Test", result.content(".a a"));
    }
}
