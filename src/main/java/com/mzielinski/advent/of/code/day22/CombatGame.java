package com.mzielinski.advent.of.code.day22;

import java.util.*;

import static java.util.stream.Collectors.toList;

record CombatGame(List<Day22.Player> players, boolean recursive, int subGame, Set<String> alreadyUsedDecks) {

    public Day22.Player play() {
        Round round = calculateCurrentRound();
        Day22.Player winner = findWinner(round);
        round.roundWinningCards(winner.name())
                .forEach(playerRound -> winner.deck().offer(playerRound.card()));

        return gameWinner()
                .orElseGet(() -> new CombatGame(players, recursive, subGame, alreadyUsedDecks).play());
    }

    private Day22.Player findWinner(Round round) {
        return recursive && round.recursiveRequired()
                ? recursiveCombat(round)
                : round.calculateSimpleRoundWinner(players);
    }

    private Round calculateCurrentRound() {
        return new Round(players.stream()
                .map(player -> {
                    Deque<Integer> deck = player.deck();
                    Integer card = deck.poll();
                    return new Round.PlayerRound(player.name(), card, deck.size());
                })
                .filter(playerRound -> playerRound.card() != null)
                .collect(toList()));
    }

    private Day22.Player recursiveCombat(Round round) {
        List<Day22.Player> subGamePlayers = round.subGamePlayers(players);
        Day22.Player winner = new CombatGame(subGamePlayers, recursive, subGame + 1, new HashSet<>()).play();
        return findPlayer(winner.name());
    }

    private Day22.Player findPlayer(String name) {
        return players.stream()
                .filter(player -> Objects.equals(name, player.name()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Player " + name + " does not exist"));
    }

    private Integer countNumberOfCards() {
        return players.stream()
                .map(Day22.Player::deck)
                .map(Deque::size)
                .reduce(0, Integer::sum);
    }

    private Optional<Day22.Player> gameWinner() {
        Day22.Player firstPlayer = players().get(0);
        if (!alreadyUsedDecks.add(firstPlayer.printDeck())) {
            return Optional.of(firstPlayer);
        }
        int allCards = countNumberOfCards();
        return players.stream()
                .filter(player -> player.deck().size() == allCards)
                .findAny();
    }
}
