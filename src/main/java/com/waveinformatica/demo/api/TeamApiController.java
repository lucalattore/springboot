package com.waveinformatica.demo.api;

import com.waveinformatica.demo.dto.TeamDTO;
import com.waveinformatica.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamApiController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/teams")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTeam(@RequestBody final TeamDTO team) {
        teamService.addTeam(team);
    }

    @PutMapping("/teams/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTeam(@PathVariable("id") long id, @RequestBody final TeamDTO team) {
        team.setId(id);
        teamService.updateTeam(team);
    }

    @PatchMapping("/teams/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchTeam(@PathVariable("id") long id, @RequestBody final TeamDTO team) {
        team.setId(id);
        teamService.updateIfPresent(team);
    }
}
