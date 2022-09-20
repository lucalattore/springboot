package com.waveinformatica.demo.api;

import com.waveinformatica.demo.dto.PlayerDTO;
import com.waveinformatica.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerApiController {

    @Autowired
    private PlayerService playerService;

    @PatchMapping("/teams/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchTeam(@PathVariable("id") long id, @RequestBody final PlayerDTO player) {
        player.setId(id);
        playerService.updateIfPresent(player);
    }
}
