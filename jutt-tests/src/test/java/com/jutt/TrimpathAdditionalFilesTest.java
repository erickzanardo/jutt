package com.jutt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.gson.JsonObject;

public class TrimpathAdditionalFilesTest extends TemplateTest {

    @Override
    public URL getEngine() {
        return Utils.fileToURL(new File("src/main/webapp/js/trimpath-template-1.0.38.js"));
    }

    @Override
    public URL getParser() {
        return Utils.fileToURL(new File("src/main/webapp/js/trimpathParser.js"));
    }

    @Override
    public List<URL> additionalFiles() {
        return Arrays.asList(Utils.fileToURL(new File("src/main/webapp/js/dummyFunction.js")));
    }

    @Test
    public void testResult() {
        JsonObject data = new JsonObject();
        data.addProperty("title", "My name is");
        data.addProperty("x", "Bond,");
        data.addProperty("y", "James Bond");

        String template = Utils.readFile("src/main/webapp/template/trimpath/dummy-template-6.html");
        TestResult result = doTemplateAsResult(template, data);
        assertEquals("My name isMy name is", result.content("h1"));
        assertEquals("Bond,Bond, James BondJames Bond", result.content(".a"));
        assertEquals("James BondJames Bond", result.content(".a a"));
    }
}