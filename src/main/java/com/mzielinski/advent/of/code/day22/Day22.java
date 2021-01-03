package com.mzielinski.advent.of.code.day22;

import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day22 {

    record Player(String name, Deque<Integer> deck) {

        public String printDeck() {
            return deck.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
        }
    }

    public int winnerScoreInCombat(String filePath, boolean recursive) {
        Player winner = findWinnerPlayer(filePath, recursive);
        return calculateWinnerScore(winner);
    }

    public String printWinningDeck(String filePath, boolean recursive) {
        return findWinnerPlayer(filePath, recursive).printDeck();
    }

    private int calculateWinnerScore(Player winner) {
        return IntStream.range(1, winner.deck().size() + 1)
                .mapToObj(index -> calculateSingleCardScore(index, winner.deck()))
                .reduce(0, Integer::sum);
    }

    private int calculateSingleCardScore(int index, Deque<Integer> deck) {
        return Optional.ofNullable(deck.pollLast())
                .map(card -> card * index)
                .orElse(0);
    }

    private Player findWinnerPlayer(String filePath, boolean recursive) {
        try {
            List<Player> players = new DecksReader().readFile(filePath);
            return new CombatGame(players, recursive, 0, new HashSet<>()).play();
        } catch (InfiniteGameException infiniteGameException) {
            final Player exceptionalWinner = infiniteGameException.getWinner();
            System.err.println("Infinite loop of the game. Player " + exceptionalWinner + " won in such case.");
            return exceptionalWinner;
        }
    }
}
