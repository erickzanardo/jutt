package com.jutt;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import com.google.gson.JsonObject;

public class JuttTest extends TemplateTest {

    @Override
    public File getEngine() {
        return new File("src/main/webapp/js/doT.js");
    }

    @Override
    public File getParser() {
        return new File("src/main/webapp/js/Parser.js");
    }

    @Test
    public void testString() {
        // dummy-template.html
        JsonObject data = new JsonObject();
        data.addProperty("title", "Hello World");

        String template = readFile("src/main/webapp/template/dummy-template.html");
        String doTemplate = doTemplate(template, data);
        assertEquals("<h1>Hello World</h1>", doTemplate);

        data = new JsonObject();
        data.addProperty("title", "Hello World \" ' ");

        template = readFile("src/main/webapp/template/dummy-template.html");
        doTemplate = doTemplate(template, data);
        assertEquals("<h1>Hello World \" ' </h1>", doTemplate);

        // dummy-template-2.html
        data = new JsonObject();
        data.addProperty("title", "Hello World");

        template = readFile("src/main/webapp/template/dummy-template-2.html");
        doTemplate = doTemplate(template, data);
        assertEquals("<h1 class=\"aaa\">Hello World</h1>", doTemplate);

        data = new JsonObject();
        data.addProperty("title", "Hello World \" ' ");

        template = readFile("src/main/webapp/template/dummy-template-2.html");
        doTemplate = doTemplate(template, data);
        assertEquals("<h1 class=\"aaa\">Hello World \" ' </h1>", doTemplate);

        // dummy-template-3.html
        data = new JsonObject();
        data.addProperty("title", "Hello World");

        template = readFile("src/main/webapp/template/dummy-template-3.html");
        doTemplate = doTemplate(template, data);
        assertEquals("<h1 class='aaa'>Hello World</h1>", doTemplate);

        data = new JsonObject();
        data.addProperty("title", "Hello World \" ' ");

        template = readFile("src/main/webapp/template/dummy-template-3.html");
        doTemplate = doTemplate(template, data);
        assertEquals("<h1 class='aaa'>Hello World \" ' </h1>", doTemplate);

    }

    private String readFile(String file) {
        BufferedReader br = null;
        StringBuilder ret = new StringBuilder();

        try {
            String line;
            br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                ret.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return ret.toString();
    }
}
