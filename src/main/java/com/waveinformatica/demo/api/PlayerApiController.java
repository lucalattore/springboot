package com.waveinformatica.demo.api;

import com.waveinformatica.demo.dto.PlayerDTO;
import com.waveinformatica.demo.exceptions.InvalidDataException;
import com.waveinformatica.demo.exceptions.ResourceNotFoundException;
import com.waveinformatica.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PlayerApiController {

    @Autowired
    private PlayerService playerService;

    @PatchMapping("/players/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchTeam(@PathVariable("id") long id, @RequestBody final PlayerDTO player) {
        player.setId(id);
        try {
            playerService.updateIfPresent(player);
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
