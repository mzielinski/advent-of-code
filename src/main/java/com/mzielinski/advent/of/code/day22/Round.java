package com.mzielinski.advent.of.code.day22;

import java.util.*;

import static java.util.stream.Collectors.toUnmodifiableList;

record Round(List<PlayerRound> players) {

    record PlayerRound(String name, Integer card, int deckSize) {
    }

    public boolean recursiveRequired() {
        return players.stream().allMatch(player -> player.card() <= player.deckSize());
    }

    public Day22.Player calculateSimpleRoundWinner(List<Day22.Player> rootGamePlayers) {
        return players.stream()
                .max(Comparator.comparingInt(PlayerRound::card))
                .map(PlayerRound::name)
                .map(name -> findPlayer(rootGamePlayers, name))
                .orElseThrow(() -> new IllegalArgumentException("Cannot find players"));
    }

    public List<Day22.Player> subGamePlayers(List<Day22.Player> rootGamePlayers) {
        return players.stream()
                .map(playerRound -> {
                    Day22.Player player = findPlayer(rootGamePlayers, playerRound.name());
                    List<Integer> subDeck = new ArrayList<>(player.deck()).subList(0, playerRound.card());
                    return new Day22.Player(player.name(), new ArrayDeque<>(subDeck));
                }).collect(toUnmodifiableList());
    }

    public List<PlayerRound> roundWinningCards(String name) {
        players.sort(roundCardsComparator(name));
        return players;
    }

    private Day22.Player findPlayer(List<Day22.Player> players, String name) {
        return players.stream()
                .filter(player -> Objects.equals(name, player.name()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Player " + name + " does not exist"));
    }
    // first on the list should be winner card, than cards of all opponents in alphabetical order

    private Comparator<PlayerRound> roundCardsComparator(String name) {
        return (player1, player2) -> {
            if (Objects.equals(player1.name(), name)) return -1;
            else if (Objects.equals(player2.name(), name)) return 1;
            return player1.name().compareTo(player2.name());
        };
    }
}
