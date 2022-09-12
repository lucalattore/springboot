package com.waveinformatica.demo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("secondoComponente")
public class MySecondComponent implements MyFirstInterface {

    private final List<String> titles;

    public MySecondComponent() {
        this.titles = new ArrayList<>();
    }

    @Override
    public String getTitle() {
        return "Ciao";
    }
}