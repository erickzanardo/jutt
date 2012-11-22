package com.jutt;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TestResult {
    private Document parse;

    public TestResult(String template) {
        parse = Jsoup.parse(template);
    }

    public String content(String selector) {
        Elements select = parse.select(selector);

        if (select != null && select.size() > 0) {
            return select.get(0).text();
        }
        return null;
    }
}
