package com.jutt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;


public class TestResultTest {

    @Test
    public void test() {
        TestResult r = new TestResult("<div><h1>Hello</h1></div>");
        String content = r.content("h1");
        assertEquals("Hello", content);

        r = new TestResult("<div><h1>Hello <span>World</span></h1></div>");
        content = r.content("h1");
        assertEquals("Hello World", content);
        content = r.content("h1 span");
        assertEquals("World", content);

        r = new TestResult(
                "<div><h1 class=\"bla\">Hello <span>World</span></h1></div>");
        content = r.content(".bla");
        assertEquals("Hello World", content);

        r = new TestResult(
                "<div><h1 class=\"bla\">Hello <span>World</span></h1></div>");
        content = r.content(".bla");
        assertEquals("Hello World", content);

        r = new TestResult(
                "<div><h1 class=\"bla\">Hello</h1><h1 class=\"ble\">World</h1></div>");
        String attr = r.attr(".bla", "class");
        assertEquals("bla", attr);

        r = new TestResult(
                "<div><h1 class=\"bla\">Hello</h1><h1 class=\"ble\">World</h1></div>");
        List<String> attrs = r.attrs("h1", "class");
        assertEquals(2, attrs.size());
        assertEquals("bla", attrs.get(0));
        assertEquals("ble", attrs.get(1));

        List<String> contents = r.contents("h1");
        assertEquals(2, contents.size());
        assertEquals("Hello", contents.get(0));
        assertEquals("World", contents.get(1));
    }

    @Test
    public void testFindElements() {
        TestResult r = new TestResult("<div><h1>Hello</h1></div>");
        Elements div = r.find("div");
        assertEquals("Hello", div.content());
        assertEquals("Hello", div.find("h1").content());
        assertNull(div.find("h2"));

        r = new TestResult(
                "<div><h1 class=\"bla\">Hello</h1><h1 class=\"ble\">World</h1></div>");
        Elements h1Bla1 = r.find(".bla");
        assertTrue(h1Bla1.hasAttr("class"));
        assertTrue(h1Bla1.hasClass("bla"));
        assertEquals("bla", h1Bla1.attr("class"));
        assertEquals("Hello", h1Bla1.content());
        assertEquals(1, h1Bla1.size());
        assertEquals(0, h1Bla1.children().size());

        r = new TestResult(
                "<div><h1 class=\"bla\">Hello</h1><h1 class=\"ble\">World</h1></div>");
        Elements h1Elements = r.find("h1");
        assertEquals(2, h1Elements.size());
        assertEquals("Hello", h1Elements.get(0).content());
        assertEquals("World", h1Elements.get(1).content());
        assertEquals("bla", h1Elements.get(0).attr("class"));
        assertEquals("ble", h1Elements.get(1).attr("class"));
        assertEquals("Hello World", h1Elements.content());

        h1Elements = r.find("div").children();
        assertEquals(2, h1Elements.size());
        assertEquals("Hello", h1Elements.get(0).content());
        assertEquals("World", h1Elements.get(1).content());
        assertEquals("bla", h1Elements.get(0).attr("class"));
        assertEquals("ble", h1Elements.get(1).attr("class"));
        assertEquals("Hello World", h1Elements.content());
    }

}
