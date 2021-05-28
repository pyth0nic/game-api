package org.resmed.game.repository;

import org.resmed.game.model.RockPaperScissors.Match;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> findByUsername(String username);
}
