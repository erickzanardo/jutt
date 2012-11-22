package com.jutt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import org.junit.Test;

import com.google.gson.JsonObject;

public class DoTTest extends TemplateTest {

    @Override
    public URL getEngine() {
        return Utils.fileToURL(new File("src/main/webapp/js/doT.js"));
    }

    @Override
    public URL getParser() {
        return Utils.fileToURL(new File("src/main/webapp/js/doTParser.js"));
    }

    @Test
    public void testResult() {
        JsonObject data = new JsonObject();
        data.addProperty("title", "My name is");
        data.addProperty("x", "Bond,");
        data.addProperty("y", "James Bond");

        String template = Utils.readFile("src/main/webapp/template/dot/dummy-template-4.html");
        TestResult result = doTemplateAsResult(template, data);
        assertEquals("My name is", result.content("h1"));
        assertEquals("Bond, James Bond", result.content(".a"));
        assertEquals("James Bond", result.content(".a a"));

        data = new JsonObject();
        data.addProperty("title", "Hello World");

        template = Utils.readFile("src/main/webapp/template/dot/dummy-template-7.html");
        result = doTemplateAsResult(template, data, "h1");
        assertEquals("Hello World", result.content("h1"));

        template = Utils.readFile("src/main/webapp/template/dot/dummy-template-7.html");
        result = doTemplateAsResult(template, data, "h2");
        assertEquals("Hello World", result.content("h2"));

    }

    @Test
    public void testString() {
        // dummy-template.html
        JsonObject data = new JsonObject();
        data.addProperty("title", "Hello World");

        String template = Utils.readFile("src/main/webapp/template/dot/dummy-template.html");
        String doTemplate = doTemplateAsString(template, data);
        assertEquals("<h1>Hello World</h1>", doTemplate);

        data = new JsonObject();
        data.addProperty("title", "Hello World \" ' ");

        template = Utils.readFile("src/main/webapp/template/dot/dummy-template.html");
        doTemplate = doTemplateAsString(template, data);
        assertEquals("<h1>Hello World \" ' </h1>", doTemplate);

        // dummy-template-2.html
        data = new JsonObject();
        data.addProperty("title", "Hello World");

        template = Utils.readFile("src/main/webapp/template/dot/dummy-template-2.html");
        doTemplate = doTemplateAsString(template, data);
        assertEquals("<h1 class=\"aaa\">Hello World</h1>", doTemplate);

        data = new JsonObject();
        data.addProperty("title", "Hello World \" ' ");

        template = Utils.readFile("src/main/webapp/template/dot/dummy-template-2.html");
        doTemplate = doTemplateAsString(template, data);
        assertEquals("<h1 class=\"aaa\">Hello World \" ' </h1>", doTemplate);

        // dummy-template-3.html
        data = new JsonObject();
        data.addProperty("title", "Hello World");

        template = Utils.readFile("src/main/webapp/template/dot/dummy-template-3.html");
        doTemplate = doTemplateAsString(template, data);
        assertEquals("<h1 class='aaa'>Hello World</h1>", doTemplate);

        data = new JsonObject();
        data.addProperty("title", "Hello World \" ' ");

        template = Utils.readFile("src/main/webapp/template/dot/dummy-template-3.html");
        doTemplate = doTemplateAsString(template, data);
        assertEquals("<h1 class='aaa'>Hello World \" ' </h1>", doTemplate);

        // Selector
        data = new JsonObject();
        data.addProperty("title", "Hello World");

        template = Utils.readFile("src/main/webapp/template/dot/dummy-template-7.html");
        doTemplate = doTemplateAsString(template, data, "h1");
        assertEquals("<h1>Hello World</h1>", doTemplate);

        template = Utils.readFile("src/main/webapp/template/dot/dummy-template-7.html");
        doTemplate = doTemplateAsString(template, data, "h2");
        assertEquals("<h2>Hello World</h2>", doTemplate);
    }

}
