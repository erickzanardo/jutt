package com.jutt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestResultTest {

    @Test
    public void test() {
        TestResult r = new TestResult("<div><h1>Hello</h1></div>");
        String content = r.content("h1");
        assertEquals("Hello", content);
    }
}
