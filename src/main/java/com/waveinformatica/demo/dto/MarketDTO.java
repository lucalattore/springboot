package com.waveinformatica.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarketDTO {
    private long id;
    private String name;
    private String area;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> brands = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<String> getBrands() {
        return brands;
    }
}
