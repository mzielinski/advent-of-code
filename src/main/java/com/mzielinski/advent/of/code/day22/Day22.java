package com.mzielinski.advent.of.code.day22;

import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Day22 {

    record Player(String name, Deque<Integer> deck) {

        public String printDeck() {
            return deck.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
        }
    }

    record PlayerRound(int player, Integer card) {

    }

    record Game(List<Player> players, int allCards) {

        public Player play() {
            List<PlayerRound> round = IntStream.range(0, players.size())
                    .mapToObj(player -> new PlayerRound(player, players().get(player).deck().poll()))
                    .filter(playerRound -> playerRound.card() != null)
                    .sorted((player1, player2) -> Integer.compare(player2.card, player1.card))
                    .collect(toList());

            // find winner fo round
            PlayerRound roundWinner = round.get(0);
            Player winner = players().get(roundWinner.player());

            // move all cards to winner of round
            round.forEach(playerRound -> winner.deck().offer(playerRound.card));

            return gameWinner(allCards)
                    .orElseGet(() -> new Game(players, allCards).play());
        }

        private Optional<Player> gameWinner(int allCards) {
            return players.stream()
                    .filter(player -> player.deck().size() == allCards)
                    .findAny();
        }
    }

    public int winnerScore(String filePath) {
        Player winner = findWinnerPlayer(filePath);
        return IntStream.range(1, winner.deck().size() + 1)
                .mapToObj(index -> calculateCardScore(index, winner.deck()))
                .reduce(0, Integer::sum);
    }

    private int calculateCardScore(int index, Deque<Integer> deck) {
        return Optional.ofNullable(deck.pollLast())
                .map(card -> card * index)
                .orElse(0);
    }

    public String printWinningDeck(String filePath) {
        return findWinnerPlayer(filePath).printDeck();
    }

    private Player findWinnerPlayer(String filePath) {
        List<Player> players = new DecksReader().readFile(filePath);
        int allCards = players.stream()
                .map(Player::deck)
                .map(Deque::size)
                .reduce(0, Integer::sum);
        return new Game(players, allCards).play();
    }
}
