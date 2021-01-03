package com.mzielinski.advent.of.code.day22;

import com.mzielinski.advent.of.code.day22.Day22.Player;

public class InfiniteGameException extends RuntimeException {

    private final Player winner;

    public InfiniteGameException(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }
}
