package org.resmed.game.service;

import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class RandomServiceImpl implements RandomService {
    private Random random;

    public RandomServiceImpl() {
        this.random = new Random();
    }

    public Random getRandom() {
        return random;
    }
}
