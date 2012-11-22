package com.jutt;

import static org.junit.Assert.assertEquals;

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

    }
}
