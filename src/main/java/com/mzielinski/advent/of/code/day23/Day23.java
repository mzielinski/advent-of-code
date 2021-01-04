package com.mzielinski.advent.of.code.day23;

import com.mzielinski.advent.of.code.day23.LinkedListWithFastGet.Node;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day23 {

    static class Circle {

        private final LinkedListWithFastGet<Integer> cups;
        private Node<Integer> current;

        Circle(List<Integer> cups) {
            this.cups = new LinkedListWithFastGet<>(cups);
            this.current = this.cups.get(cups.get(0));
        }

        public void move() {
            List<Integer> pickedUp = cups.getAfter(current.item, 3);
            int destination = findDestination(pickedUp);
            cups.moveAfter(destination, pickedUp);

            // move to next number
            current = current.next;
        }

        private int findDestination(List<Integer> pickedUp) {
            int j;
            for (j = current.item - 2 + cups.size(); j > 0; j--) {
                int n = j % cups.size() + 1;
                if (!pickedUp.contains(n)) {
                    break;
                }
            }
            return j % cups.size() + 1;
        }

        public String printCircleAfter(int mainCub) {
            List<Integer> cupsAfter = cups.getAfter(mainCub);
            return cupsAfter.stream().map(Object::toString).collect(Collectors.joining());
        }

        public long cupsWithStars(int mainCub) {
            List<Integer> cupsAfter = cups.getAfter(mainCub, 2);
            return (long) cupsAfter.get(0) * (long) cupsAfter.get(1);
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

    public long findHiddenTwoStars(int moves, Circle circle, int total, int cup) {
        List<Integer> cups = circle.cups();
        Integer max = cups.stream().max(Comparator.naturalOrder()).orElseThrow();
        IntStream.rangeClosed(max + 1, total).forEach(cups::add);
        return moveCups(moves, new Circle(cups)).cupsWithStars(cup);
    }
}
