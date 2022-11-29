package com.mzielinski.advent.of.code.day10;

import java.util.Arrays;

enum Rating {
    ONE(1), TWO(2), THREE(3);

    private final int number;

    Rating(int number) {
        this.number = number;
    }

    public static boolean isValid(long number) {
        return Arrays.stream(Rating.values())
                .filter(n -> n.number == number)
                .findFirst()
                .orElse(null) != null;
    }

    public int getNumber() {
        return number;
    }
}