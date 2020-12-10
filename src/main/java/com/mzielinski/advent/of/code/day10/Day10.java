package com.mzielinski.advent.of.code.day10;

import java.util.List;

public class Day10 {

    private final InputProcessor inputProcessor;
    private final RatingCalculator ratings;

    public Day10() {
        this.inputProcessor = new InputProcessor();
        this.ratings = new RatingCalculator();
    }

    public long findDifferencesForJoltage(String path) {
        List<Long> input = inputProcessor.readFile(path);
        ratings.calculateRatingMap(input);
        return ratings.calculateDifferencesBetween(Rating.ONE, Rating.THREE);
    }
}
