package com.waveinformatica.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class ListOfItems<T> {
    @JsonProperty("items")
    private final Collection<T> values;

    public ListOfItems(Collection<T> values) {
        this.values = values;
    }

    public Collection<T> getValues() {
        return values;
    }
}
