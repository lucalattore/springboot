package com.waveinformatica.demo.esempiogen;

import lombok.ToString;

@ToString
public class GenExample<T> {
    private String name;
    private T value;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
