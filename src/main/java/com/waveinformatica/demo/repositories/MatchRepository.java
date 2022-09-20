package com.waveinformatica.demo.repositories;

import com.waveinformatica.demo.entities.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<Match,Long> {
}
