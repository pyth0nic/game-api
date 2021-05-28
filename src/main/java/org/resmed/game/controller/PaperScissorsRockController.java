package org.resmed.game.controller;

import lombok.extern.log4j.Log4j2;
import org.resmed.game.model.RockPaperScissors.Match;
import org.resmed.game.model.RockPaperScissors.MatchAttempt;
import org.resmed.game.model.RockPaperScissors.Statistics;
import org.resmed.game.model.RockPaperScissors.User;
import org.resmed.game.repository.UserRepository;
import org.resmed.game.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@RestController
@RequestMapping("api/game")
public class PaperScissorsRockController {

    private UserRepository userRepository;

    private MatchService matchService;

    public PaperScissorsRockController(UserRepository userRepository,
                                       MatchService matchService) {
        this.userRepository = userRepository;
        this.matchService = matchService;
    }

    @PutMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (user == null || user.getUsername() == null) {
            return ResponseEntity.badRequest().body("Invalid Username");
        }

        var existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        this.userRepository.save(user);
        return ResponseEntity.ok().body("User created");
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        var users = userRepository.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping("/match")
    public ResponseEntity<Match> runAndSaveMatch(@RequestBody MatchAttempt matchAttempt) {
        var user = userRepository.findByUsername(matchAttempt.getUsername());
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        log.info(String.format("Starting match {0}", matchAttempt));
        var match = matchService.runMatch(matchAttempt.getChoice(), user);
        log.info(String.format("Match result {0}", match));

        return ResponseEntity.ok(match);
    }

    @GetMapping("/statistics/{username}")
    public ResponseEntity<Statistics> getMatchStatistics(@PathVariable String username) {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        var statistics = matchService.makeStatistics(user);
        return ResponseEntity.ok(statistics);
    }
}
