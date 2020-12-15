package com.mzielinski.advent.of.code.day14;

import org.paukov.combinatorics3.Generator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public final class MaskCalculator {

    private static Bits recalculateMask(Bits b) {
        char[] bits = Bits.convertTo36bit(Bits.convertToBinaryString(b.address));
        String newMask = b.reduce(bits, b.mask, (mask, bit) -> mask == '0' ? bit : mask);
        return new Bits(b.address, b.value, newMask, b.index);
    }

    public List<Bits> recalculateMasks(List<Bits> memory) {
        return memory.stream()
                .filter(bits -> bits.index >= 0)
                .sorted(Comparator.comparingInt(o -> o.index))
                .map(MaskCalculator::recalculateMask)
                .map(MaskCalculator::recalculateMask)
                .flatMap(this::convertPermutationsIntoAddresses)
                .collect(toList());
    }

    private Stream<Bits> convertPermutationsIntoAddresses(Bits bits) {
        String mask = bits.mask();
        return findPermutations(mask).stream()
                .map(permutation ->
                        permutation.stream()
                                .reduce(mask,
                                        (value, bit) -> value.replaceFirst("X", String.valueOf(bit)),
                                        ($1, $2) -> null))
                .map(Bits::convertToLong)
                .map(address -> new Bits(address, bits.value(), mask, bits.index));
    }

    private List<List<Integer>> findPermutations(String mask) {
        int xCounter = (int) mask.chars().filter(ch -> ch == 'X').count();
        return Generator.permutation(0, 1)
                .withRepetitions(xCounter)
                .stream()
                .collect(toList());
    }
}
