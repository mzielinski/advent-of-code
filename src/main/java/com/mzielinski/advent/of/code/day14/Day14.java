package com.mzielinski.advent.of.code.day14;

import java.util.*;

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
            memory.sort(Comparator.<Bits>comparingInt(o -> o.index).reversed());
            return filterDuplicatedByIndex().stream()
                    .filter(bits -> bits.index >= 0)
                    .map(bits -> applyMask ? bits.applyMask() : bits)
                    .map(Bits::value)
                    .reduce(Long::sum)
                    .orElse(0L);
        }

        private List<Bits> filterDuplicatedByIndex() {
            List<Bits> filtered = new ArrayList<>();
            Set<Long> alreadyUsedAddresses = new HashSet<>();
            memory.forEach(bits -> {
                if (!alreadyUsedAddresses.contains(bits.address)) {
                    filtered.add(bits);
                    alreadyUsedAddresses.add(bits.address);
                }
            });
            return filtered;
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
