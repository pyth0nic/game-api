package org.resmed.game.model.RockPaperScissors;

import lombok.Data;

@Data
public class Statistics {

    public Statistics(User user) {
        this.user = user;
    }

    private User user;
    private int wins;
    private int losses;
    private int draws;
}
