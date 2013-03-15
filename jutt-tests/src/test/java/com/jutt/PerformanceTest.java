package com.jutt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.JsonObject;

public class PerformanceTest {

    private static TemplateTest helper;

    @BeforeClass
    public static void setup() {
        URL engine = Utils.fileToURL(new File("src/main/webapp/js/doT.js"));
        URL parser = Utils.fileToURL(new File("src/main/webapp/js/doTParser.js"));
        helper = new TemplateTest(engine, parser);
    }

    @Test
    @Ignore
    public void testResult() {

        JsonObject data = new JsonObject();
        data.addProperty("title", "My name is");
        data.addProperty("x", "Bond,");
        data.addProperty("y", "James Bond");

        String template = Utils.readFile("src/main/webapp/template/dot/dummy-template-4.html");

        long milis = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            TestResult result = helper.doTemplateAsResult(template, data);
            assertEquals("My name is", result.content("h1"));
            assertEquals("Bond, James Bond", result.content(".a"));
            assertEquals("James Bond", result.content(".a a"));
            assertEquals("#", result.attr(".a a", "href"));
        }

        System.out.println(System.currentTimeMillis() - milis);
    }

}
