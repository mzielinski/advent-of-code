package com.mzielinski.advent.of.code.day23;

import com.mzielinski.advent.of.code.day23.LinkedListWithFastGet.Node;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.naturalOrder;

public class Day23 {

    static class Circle {

        private final LinkedListWithFastGet<Integer> cups;
        private final int min;
        private final int max;
        private Node<Integer> current;

        Circle(List<Integer> cups, int min, int max) {
            this.cups = new LinkedListWithFastGet<>(cups);
            this.current = this.cups.get(cups.get(0));
            this.min = min;
            this.max = max;
        }

        public void move() {
            List<Integer> pickedUp = cups.getAfter(current.item, 3);
            Integer destination = findNextDestination(pickedUp, current.item, current.item - 1);
            cups.moveAfter(destination, pickedUp);
            current = current.next;
        }

        private int findNextDestination(List<Integer> pickedUp, Integer current, Integer proposedDestination) {
            if (!Objects.equals(current, proposedDestination)
                    && !pickedUp.contains(proposedDestination)
                    && cups.contains(proposedDestination)) {
                return proposedDestination;
            }
            return findNextDestination(pickedUp, current, proposedDestination - 1 >= min ? proposedDestination - 1 : max);
        }

        public String printCircleAfter(int mainCub) {
            List<Integer> cupsAfter = cups.getAfter(mainCub);
            return cupsAfter.stream().map(Object::toString).collect(Collectors.joining());
        }

        public long cupsWithStars(int mainCub) {
            List<Integer> cupsAfter = cups.getAfter(mainCub, 2);
            return cupsAfter.get(0) * (long) cupsAfter.get(1);
        }

        public List<Integer> cups() {
            return cups.keySet();
        }
    }

    public Circle moveCups(int moves, Circle circle) {
        for (int i = 0; i < moves; i++) {
            circle.move();
        }
        return circle;
    }

    public long findHiddenTwoStars(int moves, Circle circle, int total, int mainCup) {
        List<Integer> cups = circle.cups();
        int initMin = cups.stream().min(naturalOrder()).orElseThrow();
        int initMax = cups.stream().max(naturalOrder()).orElseThrow();
        IntStream.rangeClosed(initMax + 1, total).forEach(cups::add);
        return moveCups(moves, new Circle(cups, initMin, total)).cupsWithStars(mainCup);
    }
}
