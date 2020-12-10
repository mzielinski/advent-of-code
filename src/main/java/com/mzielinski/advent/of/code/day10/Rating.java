package com.mzielinski.advent.of.code.day10;

import java.util.Arrays;

enum Rating {
    ONE(1), TWO(2), THREE(3);

    private final int number;

    Rating(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    static Rating convert(int rating) {
        return Arrays.stream(values())
                .filter(ranking -> ranking.number == rating)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("rating  number" + rating + " is not supported"));
    }
}