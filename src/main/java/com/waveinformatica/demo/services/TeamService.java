package com.waveinformatica.demo.services;

import com.waveinformatica.demo.dto.TeamDTO;
import com.waveinformatica.demo.entities.Team;
import com.waveinformatica.demo.exceptions.ResourceNotFoundException;
import com.waveinformatica.demo.repositories.TeamRepository;
import com.waveinformatica.demo.utils.OptionalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public void addTeam(TeamDTO team) {
        Team t = new Team();
        t.setTitle(OptionalUtils.value(team.getTitle()));
        t.setColor(OptionalUtils.value(team.getColor()));
        t.setCity(OptionalUtils.value(team.getCity()));
        t.setNumOfStar(OptionalUtils.value(team.getNumOfStar()));

        teamRepository.save(t);
    }

    public void updateTeam(TeamDTO team) {
//        Team t = teamRepository
//            .findById(team.getId())
//            .orElseThrow(() -> new ResourceNotFoundException("Team non trovato"));
// EQUIVALENTE A QUANTO SEGUE

        Optional<Team> foundTeam = teamRepository.findById(team.getId());
        if (!foundTeam.isPresent()) {
            throw new ResourceNotFoundException("Team non trovato");
        }

        Team t = foundTeam.get();
        t.setTitle(OptionalUtils.value(team.getTitle()));
        t.setColor(OptionalUtils.value(team.getColor()));
        t.setCity(OptionalUtils.value(team.getCity()));
        t.setNumOfStar(OptionalUtils.value(team.getNumOfStar()));

        teamRepository.save(t);
    }

    public void updateIfPresent(TeamDTO team) {
        Optional<Team> foundTeam = teamRepository.findById(team.getId());
        if (!foundTeam.isPresent()) {
            throw new ResourceNotFoundException("Team non trovato");
        }

        Team t = foundTeam.get();

        // aggiorniamo solo i campi presenti
        if (team.getTitle() != null) {
            t.setTitle(OptionalUtils.value(team.getTitle()));
        }

        if (team.getColor() != null) {
            t.setColor(OptionalUtils.value(team.getColor()));
        }

        if (team.getCity() != null) {
            t.setCity(OptionalUtils.value(team.getCity()));
        }

        if (team.getNumOfStar() != null) {
            t.setNumOfStar(OptionalUtils.value(team.getNumOfStar()));
        }

        teamRepository.save(t);
    }

    public void listAllByScoring() {
        //TODO: completare
    }
}
