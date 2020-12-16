package com.mzielinski.advent.of.code.day14;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record Day14() {

    record Memory(List<Bits> memory) {

        public long puzzle1() {
            return sumValues(true);
        }

        public long puzzle2() {
            List<Bits> memory = new MaskCalculator().recalculateMasks(memory());
            return new Memory(memory).sumValues(false);
        }

        private long sumValues(boolean applyMask) {
            // remove duplicated addresses (leave the last occurrence)
            Set<Bits> filtered = memory.stream()
                    .sorted(Comparator.<Bits>comparingInt(o -> o.index).reversed())
                    .collect(Collectors.toUnmodifiableSet());

            // calculate sum from values which left
            return filtered.stream()
                    .filter(bits -> bits.index >= 0)
                    .map(bits -> applyMask ? bits.applyMask() : bits)
                    .map(Bits::value)
                    .reduce(Long::sum)
                    .orElse(0L);
        }
    }

    public long solvePuzzle(String filePath, String puzzle) {
        Memory memory = new Memory(new ProgramReader().readFile(filePath));
        return switch (puzzle) {
            case "puzzle1" -> memory.puzzle1();
            case "puzzle2" -> memory.puzzle2();
            default -> throw new IllegalArgumentException("Unsupported puzzle " + puzzle);
        };
    }
}
