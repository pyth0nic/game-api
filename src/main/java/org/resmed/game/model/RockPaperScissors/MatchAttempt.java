package org.resmed.game.model.RockPaperScissors;

import lombok.Data;

@Data
public class MatchAttempt {
    private String username;
    private Choice choice;
}
