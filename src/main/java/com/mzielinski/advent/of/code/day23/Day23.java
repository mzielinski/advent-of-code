package com.mzielinski.advent.of.code.day23;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day23 {

    // current cub is always first on the list
    record Circle(List<Integer> cubs) {

        public Circle move() {
            List<Integer> pickUps = cubs.subList(1, 4);
            int destinationCub = findDestination(pickUps, cubs.get(0) - 1, cubs.get(0));
            List<Integer> cubsAfterMove = new ArrayList<>();
            IntStream.range(4, cubs.size())
                    .forEach(index -> {
                        int nextCub = cubs.get(index);
                        cubsAfterMove.add(nextCub);
                        if (nextCub == destinationCub) {
                            cubsAfterMove.addAll(pickUps);
                        }
                    });
            cubsAfterMove.add(cubs.get(0));
            return new Circle(cubsAfterMove);
        }

        private int findDestination(List<Integer> pickups, int proposedDestination, int currentCub) {
            if (proposedDestination < minCub()) {
                return findDestination(pickups, maxCub(), currentCub);
            }
            return validDestinationCub(pickups, proposedDestination, currentCub)
                    ? proposedDestination
                    : findDestination(pickups, proposedDestination - 1, currentCub);
        }

        private boolean validDestinationCub(List<Integer> pickups, int destination, int currentCub) {
            return !pickups.contains(destination)
                    && destination != currentCub
                    && cubs.contains(destination);
        }

        private int minCub() {
            return cubs.stream()
                    .min(Integer::compareTo)
                    .orElseThrow(() -> new IllegalArgumentException("Cannot find max value for " + cubs));
        }

        private int maxCub() {
            return cubs.stream()
                    .max(Integer::compareTo)
                    .orElseThrow(() -> new IllegalArgumentException("Cannot find max value for " + cubs));
        }

        public String after(int cub) {
            int index = cubs.indexOf(cub);
            List<Integer> order = cubs.subList(index + 1, cubs.size());
            order.addAll(cubs.subList(0, index));
            return order.stream().map(Object::toString).collect(Collectors.joining());
        }
    }

    public Circle moveCubs(int moves, Circle circle) {
        return moves == 0 ? circle : moveCubs(moves - 1, circle.move());
    }
}
