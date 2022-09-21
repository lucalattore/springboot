package com.waveinformatica.demo.api;

import com.waveinformatica.demo.dto.MatchDTO;
import com.waveinformatica.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchApiController {

    @Autowired
    private MatchService matchService;

    @PostMapping("/matches")
    public void addMatch(@RequestBody final MatchDTO match) {
        matchService.registerMatch(match);
    }

}
