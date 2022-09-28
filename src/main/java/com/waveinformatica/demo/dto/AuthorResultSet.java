package com.waveinformatica.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AuthorResultSet {
    private final List<AuthorDTO> values;

    public AuthorResultSet() {
        this.values = new ArrayList<>();
    }
}
