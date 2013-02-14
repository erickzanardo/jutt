package com.jutt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;

public class MockObjectsTest {

    private static TemplateTest helper;

    @BeforeClass
    public static void setup() {
        URL engine = Utils.fileToURL(new File("src/main/webapp/js/doT.js"));
        URL parser = Utils.fileToURL(new File("src/main/webapp/js/doTParser.js"));
        helper = new TemplateTest(engine, parser);
    }

    @Test
    public void testResult() {
        JsonObject data = new JsonObject();
        data.addProperty("title", "Rhino!");

        MockObject m1 = new MockObject("a").asValue("TEST");
        MockObject m2 = new MockObject("bla").asFunction("TESTFUNCTION");

        MockObject m3 = new MockObject("b.b").asValue("TESTPATH");
        MockObject m4 = new MockObject("b.bla").asFunction("TESTFUNCTIONPATH");

        MockObject m5 = new MockObject("b.c.d.number").asFunction(3);
        MockObject m6 = new MockObject("b.c.d.boolean").asFunction(true);

        String template = Utils.readFile("src/main/webapp/template/mock/mock-template.html");
        TestResult result = helper.doTemplateAsResult(template, data, m1, m2, m3, m4, m5, m6);

        assertEquals("Rhino!TEST", result.content("h1"));
        assertEquals("TESTFUNCTION", result.content("h2"));

        assertEquals("Rhino!TESTPATH", result.content("h1.path"));
        assertEquals("TESTFUNCTIONPATH", result.content("h2.path"));

        assertEquals("3", result.content("h1.pathnumber"));
        assertEquals("true", result.content("h2.pathboolean"));
    }

}
