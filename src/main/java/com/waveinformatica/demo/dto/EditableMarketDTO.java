package com.waveinformatica.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
public class EditableMarketDTO {
    private Optional<String> name;
    private Optional<String> area;
}
