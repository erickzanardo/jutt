package com.jutt;

import java.util.ArrayList;
import java.util.Collection;

import org.jsoup.nodes.Element;

public class Elements {

    private org.jsoup.select.Elements jsoupElements;

    private Elements(org.jsoup.nodes.Element jsoupElement) {
        this.jsoupElements = new org.jsoup.select.Elements(jsoupElement);
    }

    protected Elements(org.jsoup.select.Elements jsoupElements) {
        this.jsoupElements = jsoupElements;
    }

    public String content() {
        return jsoupElements.text();
    }

    public String attr(String attributeKey) {
        return jsoupElements.attr(attributeKey);
    }

    public Elements children() {
        Collection<Element> elements = new ArrayList<Element>();
        for (Element element : jsoupElements) {
            elements.addAll(element.children());
        }
        jsoupElements = new org.jsoup.select.Elements(elements);
        return this;
    }

    public int size() {
        return jsoupElements.size();
    }

    public Elements get(int index) {
        return new Elements(jsoupElements.get(index));
    }
    
    public boolean hasAttr(String attributeKey) {
        return jsoupElements.hasAttr(attributeKey);
    }

    public boolean hasClass(String className) {
        return jsoupElements.hasClass(className);
    }

    public Elements find(String cssQuery) {
        org.jsoup.select.Elements select = jsoupElements.select(cssQuery);

        if (select != null && select.size() > 0) {
            jsoupElements = select;
            return this;
        }
        return null;
    }

}
