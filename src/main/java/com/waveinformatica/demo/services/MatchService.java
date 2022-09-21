package com.waveinformatica.demo.services;

import com.waveinformatica.demo.dto.MatchDTO;
import com.waveinformatica.demo.entities.Match;
import com.waveinformatica.demo.entities.Team;
import com.waveinformatica.demo.exceptions.InvalidDataException;
import com.waveinformatica.demo.repositories.MatchRepository;
import com.waveinformatica.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public void registerMatch(MatchDTO match) {
        Team a = teamRepository
            .findById(match.getTeamA())
            .orElseThrow(() -> new InvalidDataException("Team not found: " + match.getTeamA()));

        Team b = teamRepository
            .findById(match.getTeamB())
            .orElseThrow(() -> new InvalidDataException("Team not found: " + match.getTeamB()));

        Match m = new Match();
        m.setTeamA(a);
        m.setTeamB(b);
        m.setScoreA(match.getScoreA());
        m.setScoreB(match.getScoreB());
        matchRepository.save(m);

        if (match.getScoreA() == match.getScoreB()) {
            // pareggio
            a.setP(a.getP() + 1);
            a.setRank(a.getRank() + 1);

            b.setP(b.getP() + 1);
            b.setRank(b.getRank() + 1);
        } else if (match.getScoreA() > match.getScoreB()) {
            // vittoria di A
            a.setV(a.getV() + 1);
            a.setRank(a.getRank() + 3);

            b.setS(b.getS() + 1);
        } else {
            // sconfitta di A
            a.setS(a.getS() + 1);

            b.setV(b.getV() + 1);
            b.setRank(b.getRank() + 3);
        }

        teamRepository.save(a);
        teamRepository.save(b);
    }
}
