package com.mzielinski.advent.of.code.day10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 {

    private final RatingCalculator calculator = new RatingCalculator();
    private final List<Long> sortedInputWithChargingOutlet;

    public Day10(String path) {
        this.sortedInputWithChargingOutlet = sortedInputWithChargingOutlet(new InputProcessor().readFile(path));
    }

    public long findDifferencesForJoltage() {
        Map<Long, Rating> ratings = calculator.calculateRatingMap(sortedInputWithChargingOutlet);
        return calculator.calculateDifferencesBetween(ratings, Rating.ONE, Rating.THREE);
    }

    public long findArrangementForDevice() {
        return calculator.calculateArrangements(sortedInputWithChargingOutlet, new HashMap<>());
    }

    private List<Long> sortedInputWithChargingOutlet(List<Long> input) {
        List<Long> list = new ArrayList<>(input);
        list.add(0L);
        list.sort(Long::compareTo);
        return list;
    }
}
