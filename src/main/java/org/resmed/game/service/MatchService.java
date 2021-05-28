package org.resmed.game.service;

import org.resmed.game.model.RockPaperScissors.Choice;
import org.resmed.game.model.RockPaperScissors.Match;
import org.resmed.game.model.RockPaperScissors.Statistics;
import org.resmed.game.model.RockPaperScissors.User;

public interface MatchService {
    Match runMatch(Choice choice, User user);
    Statistics makeStatistics(User username);
}
