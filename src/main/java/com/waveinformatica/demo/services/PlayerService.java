package com.waveinformatica.demo.services;

import com.waveinformatica.demo.dto.PlayerDTO;
import com.waveinformatica.demo.entities.Player;
import com.waveinformatica.demo.entities.Team;
import com.waveinformatica.demo.exceptions.InvalidDataException;
import com.waveinformatica.demo.exceptions.ResourceNotFoundException;
import com.waveinformatica.demo.repositories.PlayerRepository;
import com.waveinformatica.demo.repositories.TeamRepository;
import com.waveinformatica.demo.utils.OptionalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public void updateIfPresent(PlayerDTO player) {
        Optional<Player> foundPlayer = playerRepository.findById(player.getId());
        if (!foundPlayer.isPresent()) {
            throw new ResourceNotFoundException("Player non trovato");
        }

        Player p = foundPlayer.get();

        if (player.getFirstName() != null) {
            p.setFirstName(OptionalUtils.value(player.getFirstName()));
        }

        if (player.getLastName() != null) {
            p.setLastName(OptionalUtils.value(player.getLastName()));
        }

        //TODO: altri attributi

        if (player.getTeam() != null) {
            // cambio squadra
            if (player.getTeam().isEmpty()) {
                p.setTeam(null);
            } else {
                Long teamId = player.getTeam().get().getId();
                if (teamId == null) {
                    throw new InvalidDataException("Missing team ID");
                }

                Optional<Team> foundTeam = teamRepository.findById(teamId);
                if (!foundTeam.isPresent()) {
                    throw new InvalidDataException("Unknown team ID");
                }

                p.setTeam(foundTeam.get());
            }
        }

        playerRepository.save(p);
    }
}
