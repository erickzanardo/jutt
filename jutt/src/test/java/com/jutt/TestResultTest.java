package com.jutt;

import static org.junit.Assert.assertEquals;

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

        r = new TestResult("<div><h1 class='bla'>Hello <span>World</span></h1></div>");
        content = r.content(".bla");
        assertEquals("Hello World", content);

        r = new TestResult("<div><h1 class=\"bla\">Hello <span>World</span></h1></div>");
        content = r.content(".bla");
        assertEquals("Hello World", content);

        r = new TestResult("<div><h1 class=\"bla\">Hello</h1><h1 class=\"ble\">World</h1></div>");
        String attr = r.attr(".bla", "class");
        assertEquals("bla", attr);

        r = new TestResult("<div><h1 class=\"bla\">Hello</h1><h1 class=\"ble\">World</h1></div>");
        List<String> attrs = r.attrs("h1", "class");
        assertEquals(2, attrs.size());
        assertEquals("bla", attrs.get(0));
        assertEquals("ble", attrs.get(1));

        List<String> contents = r.contents("h1");
        assertEquals(2, contents.size());
        assertEquals("Hello", contents.get(0));
        assertEquals("World", contents.get(1));

    }
}
