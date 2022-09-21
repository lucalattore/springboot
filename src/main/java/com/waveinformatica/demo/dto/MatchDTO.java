package com.waveinformatica.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchDTO {
    private long teamA;
    private long teamB;
    private int scoreA;
    private int scoreB;
}
