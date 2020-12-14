package com.mzielinski.advent.of.code.day14;

import java.util.List;
import java.util.stream.IntStream;

public class Day14 {

    record Memory(List<Bit> memory) {
        public long sum() {
            return memory.stream()
                    .map(Bit::applyMask)
                    .reduce(Long::sum)
                    .orElse(0L);
        }
    }

    public record Bit(Integer decimal, char[] mask) {

        public static final Bit NULL = new Bit(0, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray());

        public long applyMask() {
            char[] bits = convertTo36bit(binaryString());
            return Long.parseLong(applyMask(bits), 2);
        }

        private String applyMask(char[] bits) {
            return IntStream.range(0, bits.length)
                    .map(i -> List.of('0', '1').contains(mask[i]) ? mask[i] : bits[i])
                    .mapToObj(i -> (char) i)
                    .map(String::valueOf)
                    .reduce("", (partialResult, value) -> partialResult + value);
        }

        private char[] convertTo36bit(String binary) {
            return String.format("%0" + (36 - binary.length()) + "d%s", 0, binary).toCharArray();
        }

        private String binaryString() {
            return Integer.toBinaryString(decimal);
        }
    }

    public Long sumAllAddresses(String filePath) {
        Memory memory = new ProgramReader().readFile(filePath);
        return memory.sum();
    }
}
