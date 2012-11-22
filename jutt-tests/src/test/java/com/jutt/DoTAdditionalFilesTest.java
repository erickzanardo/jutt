package com.jutt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.gson.JsonObject;

public class DoTAdditionalFilesTest extends TemplateTest {

    @Override
    public File getEngine() {
        return new File("src/main/webapp/js/doT.js");
    }

    @Override
    public File getParser() {
        return new File("src/main/webapp/js/doTParser.js");
    }

    @Override
    public List<File> additionalFiles() {
        return Arrays.asList(new File("src/main/webapp/js/dummyFunction.js"));
    }

    @Test
    public void testResult() {
        JsonObject data = new JsonObject();
        data.addProperty("title", "My name is");
        data.addProperty("x", "Bond,");
        data.addProperty("y", "James Bond");

        String template = Utils
                .readFile("src/main/webapp/template/dot/dummy-template-6.html");
        TestResult result = doTemplateAsResult(template, data);
        assertEquals("My name isMy name is", result.content("h1"));
        assertEquals("Bond,Bond, James BondJames Bond", result.content(".a"));
        assertEquals("James BondJames Bond", result.content(".a a"));
    }
}
