package com.waveinformatica.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class PlayerDTO {
    private Long id;
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<String> nickname;
    private Optional<Integer> num;
    private Optional<TeamDTO> team;
}
