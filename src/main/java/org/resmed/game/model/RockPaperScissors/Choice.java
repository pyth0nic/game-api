package org.resmed.game.model.RockPaperScissors;

public enum Choice {
    SCISSORS(0),
    ROCK(1),
    PAPER(2);

    private int value;

    Choice(int value) {
        this.value = value;
    }

    public final Integer getValue() {
        return value;
    }

    public static Choice getChoice(int value) {
        return values()[value];
    }
}
