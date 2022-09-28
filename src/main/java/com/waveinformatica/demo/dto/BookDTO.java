package com.waveinformatica.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
public class BookDTO {
    private Long id;

    @JsonProperty("title")
    private Optional<String> optionalTitle;

    @JsonProperty("isbn")
    private Optional<String> optionalISBN;

    private AuthorDTO author;
}
