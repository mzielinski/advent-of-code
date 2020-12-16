package com.mzielinski.advent.of.code.day14;

import com.mzielinski.advent.of.code.utils.AutoPopulatingList;
import com.mzielinski.advent.of.code.utils.ReadFile;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public record ProgramReader() {

    private final static Pattern MASK_PATTERN = Pattern.compile("[X|0-1]{36}");
    private final static Pattern MEMORY_PATTERN = Pattern.compile("\\d+");

    List<Bits> readFile(String filePath) {
        List<Bits> memory = new AutoPopulatingList(element -> element);
        InputStream stream = requireNonNull(ReadFile.class.getClassLoader().getResourceAsStream(filePath));
        try (Scanner scanner = new Scanner(stream)) {
            String mask = "";
            int counter = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                Matcher maskMatcher = MASK_PATTERN.matcher(line);
                if (maskMatcher.find()) {
                    mask = maskMatcher.group();
                } else {
                    Matcher memoryMatcher = MEMORY_PATTERN.matcher(line);
                    long address = memoryMatcher.find()
                            ? Long.parseLong(memoryMatcher.group())
                            : -1L;
                    long value = memoryMatcher.find()
                            ? Long.parseLong(memoryMatcher.group())
                            : -1L;
                    Bits bit = new Bits(address, value, mask, counter++);
                    memory.add(bit);
                }
            }
        }
        return memory;
    }
}
