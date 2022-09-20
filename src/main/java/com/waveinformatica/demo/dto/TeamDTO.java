package com.waveinformatica.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class TeamDTO {
    private Long id;
    private Optional<String> title;
    private Optional<String> color;
    private Optional<String> city;
    private Optional<Integer> numOfStar;
}
