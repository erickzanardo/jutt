package com.jutt;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class TestResult {
    private Document parse;

    public TestResult(String template) {
        parse = Jsoup.parse(template);
    }

    public Elements find(String selector) {
        return new Elements(parse.select(selector));
    }

    public String content(String selector) {
        org.jsoup.select.Elements select = parse.select(selector);

        if (singleResultOn(select)) {
            return select.get(0).text();
        }
        return null;
    }

    public String attr(String selector, String attr) {
        org.jsoup.select.Elements select = parse.select(selector);

        if (singleResultOn(select)) {
            return select.get(0).attr(attr);
        }
        return null;
    }

    private boolean singleResultOn(org.jsoup.select.Elements select) {
        return select != null && select.size() > 0;
    }

    public List<String> contents(String selector) {
        org.jsoup.select.Elements select = parse.select(selector);
        List<String> ret = new ArrayList<String>();
        for (org.jsoup.nodes.Element element : select) {
            ret.add(element.text());
        }
        return ret;
    }

    public List<String> attrs(String selector, String attr) {
        org.jsoup.select.Elements select = parse.select(selector);
        List<String> ret = new ArrayList<String>();
        for (org.jsoup.nodes.Element element : select) {
            ret.add(element.attr(attr));
        }
        return ret;
    }

}
