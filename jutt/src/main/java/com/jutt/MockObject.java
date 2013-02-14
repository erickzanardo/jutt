package com.jutt;

public class MockObject {
    private String object;

    private boolean function;
    private Object value;

    public MockObject(String object) {
        this.object = object;
    }

    public MockObject asFunction(Object value) {
        this.value = value;
        this.function = true;
        return this;
    }

    public MockObject asValue(Object value) {
        this.value = value;
        return this;
    }

    public String getObject() {
        return object;
    }

    public boolean isFunction() {
        return function;
    }

    public Object getValue() {
        return value;
    }

}
