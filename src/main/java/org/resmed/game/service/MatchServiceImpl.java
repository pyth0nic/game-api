package org.resmed.game.service;

import org.resmed.game.model.RockPaperScissors.*;
import org.resmed.game.repository.MatchRepository;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {

    private MatchRepository matchRepository;

    private RandomService randomService;

    public MatchServiceImpl(MatchRepository matchRepository,
                            RandomService randomService) {
        this.matchRepository = matchRepository;
        this.randomService = randomService;
    }

    @Override
    public Match runMatch(Choice choice, User user) {
        var opponentChoice = Choice.getChoice(randomService.getRandom().nextInt(3));
        var matchResult = getResult(choice, opponentChoice);
        var match = Match.builder()
                .playerChoice(choice)
                .opponentChoice(opponentChoice)
                .result(matchResult)
                .username(user.getUsername())
                .build();

        matchRepository.save(match);
        return match;
    }

    private RESULT getResult(Choice userChoice, Choice opponentChoice) {
        switch (userChoice.getValue() | (opponentChoice.getValue()<<4)) {
            case 0:
            case 0x11:
            case 0x22:
                return RESULT.DRAW;
            case 0x02:  // computer:scissors, player:paper
            case 0x10:
            case 0x21:
                return RESULT.LOSS;
            case 0x01:
            case 0x12:
            case 0x20:
                return RESULT.WIN;
        }
        return null;
    }

    @Override
    public Statistics makeStatistics(User user) {
        var matches = matchRepository.findByUsername(user.getUsername());
        if (matches.isEmpty()) {
            return null;
        }

        Statistics statistics = new Statistics(user);
        matches.stream().forEach(x-> {
            if (x.getResult() == RESULT.WIN) {
                statistics.setWins(statistics.getWins() + 1);
            } else if (x.getResult() == RESULT.LOSS) {
                statistics.setLosses(statistics.getLosses() + 1);
            } else {
                statistics.setDraws(statistics.getDraws() + 1);
            }
        });
        return statistics;
    }
}
