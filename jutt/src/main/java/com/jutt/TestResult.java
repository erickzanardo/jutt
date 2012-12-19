package com.jutt;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    public String attr(String selector, String attr) {
        Elements select = parse.select(selector);

        if (select != null && select.size() > 0) {
            return select.get(0).attr(attr);
        }
        return null;
    }

    public List<String> contents(String selector) {
        Elements select = parse.select(selector);
        List<String> ret = new ArrayList<String>();
        for (Element element : select) {
            ret.add(element.text());
        }
        return ret;
    }

    public List<String> attrs(String selector, String attr) {
        Elements select = parse.select(selector);
        List<String> ret = new ArrayList<String>();
        for (Element element : select) {
            ret.add(element.attr(attr));
        }
        return ret;
    }

}
