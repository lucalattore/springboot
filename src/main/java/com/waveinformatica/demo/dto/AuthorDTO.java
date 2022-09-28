package com.waveinformatica.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
public class AuthorDTO {
    private Long id;
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<String> nationality;
}
