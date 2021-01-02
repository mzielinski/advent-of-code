package com.mzielinski.advent.of.code.day22;

import com.mzielinski.advent.of.code.utils.MultilineReadFile;

import java.util.ArrayDeque;
import java.util.Arrays;

import static java.util.stream.Collectors.toCollection;

class DecksReader implements MultilineReadFile<Day22.Player> {

    @Override
    public boolean isNextLine(String line) {
        return line.equals("");
    }

    @Override
    public String delimiter() {
        return ",";
    }

    @Override
    public Day22.Player convertLine(String nextLine, int lineNumber) {
        String[] player = nextLine.split(delimiter());
        String[] deck = Arrays.copyOfRange(player, 1, player.length);
        return new Day22.Player(player[0], Arrays.stream(deck)
                .map(Integer::parseInt)
                .collect(toCollection(ArrayDeque::new)));
    }
}
