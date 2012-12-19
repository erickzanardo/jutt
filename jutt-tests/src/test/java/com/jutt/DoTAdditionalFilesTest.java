package com.jutt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;

public class DoTAdditionalFilesTest {

    private static TemplateTest helper;

    @BeforeClass
    public static void setup() {
        URL engine = Utils.fileToURL(new File("src/main/webapp/js/doT.js"));
        URL parser = Utils.fileToURL(new File("src/main/webapp/js/doTParser.js"));
        helper = new TemplateTest(engine, parser);
        
        URL dummyFunction = Utils.fileToURL(new File("src/main/webapp/js/dummyFunction.js"));
        helper.addAdditionalFile(dummyFunction);
    }

    @Test
    public void testResult() {
        JsonObject data = new JsonObject();
        data.addProperty("title", "My name is");
        data.addProperty("x", "Bond,");
        data.addProperty("y", "James Bond");

        String template = Utils.readFile("src/main/webapp/template/dot/dummy-template-6.html");
        TestResult result = helper.doTemplateAsResult(template, data);
        assertEquals("My name isMy name is", result.content("h1"));
        assertEquals("Bond,Bond, James BondJames Bond", result.content(".a"));
        assertEquals("James BondJames Bond", result.content(".a a"));
    }
}
