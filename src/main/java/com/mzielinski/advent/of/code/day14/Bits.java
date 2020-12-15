package com.mzielinski.advent.of.code.day14;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class Bits {

    public static final Bits NULL = new Bits(0, 0L, defaultMask(), -1);

    protected final long address;
    protected final long value;
    protected final char[] mask;
    protected final int index;

    public Bits(long address, long value, String mask, int index) {
        this(address, value, mask.toCharArray(), index);
    }

    public Bits(long address, long value, char[] mask, int index) {
        this.address = address;
        this.value = value;
        this.mask = mask;
        this.index = index;
    }

    Bits applyMask() {
        char[] bits = convertTo36bit(convertToBinaryString(value));
        String binaryString = reduce(bits, mask, (mask, bit) -> List.of('0', '1').contains(mask) ? mask : bit);
        return new Bits(address, convertToLong(binaryString), mask, index);
    }

    String reduce(char[] bits, char[] mask, BiFunction<Character, Character, Character> mapper) {
        return IntStream.range(0, mask.length)
                .map(i -> mapper.apply(mask[i], bits[i]))
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .reduce("", (partialResult, value) -> partialResult + value);
    }

    static char[] convertTo36bit(String binary) {
        return String.format("%0" + (36 - binary.length()) + "d%s", 0, binary).toCharArray();
    }

    static String convertToBinaryString(long number) {
        return Long.toBinaryString(number);
    }

    static long convertToLong(String binaryString) {
        return Long.parseLong(binaryString, 2);
    }

    public long value() {
        return value;
    }

    public String mask() {
        return String.valueOf(mask);
    }

    static String defaultMask() {
        return IntStream.range(0, 36).mapToObj(i -> "X").reduce("", String::concat);
    }

    @Override
    public String toString() {
        return "address=" + address +
                ", value=" + value +
                ", mask=" + Arrays.toString(mask) +
                ", index=" + index;
    }
}
