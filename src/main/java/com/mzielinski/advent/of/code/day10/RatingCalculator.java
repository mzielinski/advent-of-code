package com.mzielinski.advent.of.code.day10;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RatingCalculator {

    private final Map<Long, Rating> ratings = new HashMap<>();

    long calculateDifferencesBetween(Rating... rating) {
        Map<Rating, Long> sizes = new HashMap<>();
        ratings.forEach((key, value) -> {
            for (Rating r : rating) {
                if (r == value) {
                    Long currentSize = sizes.getOrDefault(r, 0L);
                    sizes.put(r, ++currentSize);
                }
            }
        });
        return sizes.values().stream()
                .reduce(1L, (partialResult, size) -> partialResult * size);
    }

    void calculateRatingMap(List<Long> input) {
        // I am not proud of this implementation. I will try to rewrite it when I will have more time ;)
        input.add(0L);
        input.sort(Long::compareTo);
        for (Rating rating : Rating.values()) {
            for (long joltage : input) {
                if (input.contains(joltage + rating.getNumber())) {
                    if (!ratings.containsKey(joltage)) {
                        ratings.put(joltage, rating);
                    }
                }
            }
        }
        ratings.put(findMaxValue(input), Rating.THREE);
        System.out.println(ratings);
    }

    private long findMaxValue(List<Long> input) {
        return input.stream()
                .mapToLong(a -> a)
                .summaryStatistics()
                .getMax();
    }

    @Override
    public String toString() {
        return ratings.toString();
    }
}
