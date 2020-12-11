package com.mzielinski.advent.of.code.day10;

import java.util.*;

class RatingCalculator {

    long calculateDifferencesBetween(Map<Long, Rating> input, Rating... ratings) {
        Map<Rating, Long> sizes = new HashMap<>();
        input.forEach((key, value) -> {
            for (Rating rating : ratings) {
                if (rating == value) {
                    Long currentSize = sizes.getOrDefault(rating, 0L);
                    sizes.put(rating, ++currentSize);
                }
            }
        });
        return sizes.values().stream()
                .reduce(1L, (partialResult, size) -> partialResult * size);
    }

    Map<Long, Rating> calculateRatingMap(List<Long> list) {
        Map<Long, Rating> ratings = new HashMap<>();
        for (Rating rating : Rating.values()) {
            for (long joltage : list) {
                if (list.contains(joltage + rating.getNumber())) {
                    if (!ratings.containsKey(joltage)) {
                        ratings.put(joltage, rating);
                    }
                }
            }
        }
        ratings.put(list.get(list.size() - 1), Rating.THREE);
        return ratings;
    }

    long calculateArrangements(List<Long> input, Map<Integer, Long> cache) {
        Integer hash = generateHashFromList(input);
        if (cache.containsKey(hash)) {
            return cache.get(hash);
        }
        long result = 1;
        for (int i = 1; i < input.size() - 1; i++) {
            Long previous = input.get(i - 1);
            Long next = input.get(i + 1);
            if (Rating.isValid(next - previous)) {
                List<Long> subList = new ArrayList<>(List.of(previous));
                subList.addAll(input.subList(i + 1, input.size()));
                result += calculateArrangements(subList, cache);
            }
        }
        cache.put(hash, result);
        return result;
    }

    private Integer generateHashFromList(List<Long> input) {
        return Objects.hash(input.toArray());
    }
}
