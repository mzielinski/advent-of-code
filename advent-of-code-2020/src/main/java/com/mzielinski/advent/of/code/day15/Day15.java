package com.mzielinski.advent.of.code.day15;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class Day15 {

    record Turn(int number, int index, int occurred) {

        public boolean isNotLast(int lastTurn) {
            return index < lastTurn - 1;
        }

        public Turn occurred(int occurred) {
            return new Turn(number, index, occurred);
        }
    }

    record MemoryGame(int lastTurn, List<Integer> sequence) {

        public static final int DEFAULT_VALUE = 0;
        public static final int NOT_OCCURRED = -1;

        int play() {
            Map<Integer, Turn> memory = IntStream.range(0, sequence.size())
                    .boxed()
                    .collect(toMap(sequence::get,
                            index -> new Turn(sequence.get(index), index, NOT_OCCURRED)));

            Turn turn = new Turn(sequence.get(sequence.size() - 1), sequence.size(), NOT_OCCURRED);
            int index = sequence.size();
            while (turn.isNotLast(lastTurn)) {
                turn = round(memory, turn, index++);
            }
            return turn.number;
        }

        Turn round(Map<Integer, Turn> memory, Turn currentTurn, int nextIndex) {
            Turn turn = findOccurredTurn(memory, currentTurn)
                    .map(occurredTurn -> {
                        // calculate new number
                        int number = calculateNumberForOccurred(nextIndex, occurredTurn);
                        return new Turn(number, nextIndex, memory.containsKey(number) ? memory.get(number).index : NOT_OCCURRED);
                    })
                    .orElseGet(() -> {
                        if (memory.containsKey(currentTurn.number)) {
                            // mark number as already occurred
                            Turn occurred = memory.get(currentTurn.number).occurred(nextIndex);
                            memory.put(currentTurn.number, occurred);
                        }
                        return firstOccurrence(memory, nextIndex);
                    });
            memory.put(turn.number, turn);
            return turn;
        }

        private Turn firstOccurrence(Map<Integer, Turn> memory, int nextIndex) {
            return new Turn(DEFAULT_VALUE,
                    nextIndex,
                    memory.containsKey(DEFAULT_VALUE) ? memory.get(DEFAULT_VALUE).index : NOT_OCCURRED);
        }

        private int calculateNumberForOccurred(int nextIndex, Turn turn) {
            return nextIndex - turn.occurred - 1;
        }

        private Optional<Turn> findOccurredTurn(Map<Integer, Turn> memory, Turn turn) {
            if (memory.containsKey(turn.number())) {
                return Optional.of(memory.get(turn.number()))
                        .filter(t -> t.occurred > NOT_OCCURRED);
            }
            return Optional.empty();
        }
    }

    public Integer memoryGame(int lastTurn, List<Integer> sequence) {
        return new MemoryGame(lastTurn, sequence).play();
    }
}
