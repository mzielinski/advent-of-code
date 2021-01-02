package com.mzielinski.advent.of.code.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Objects.requireNonNull;

public interface MultilineReadFile<T> extends ReadFile<T> {

    default String delimiter() {
        return ";";
    }

    default boolean isNextLine(String line) {
        return "".equals(line);
    }

    default List<T> readFile(String filePath) {
        List<T> records = new ArrayList<>();
        InputStream stream = requireNonNull(MultilineReadFile.class.getClassLoader().getResourceAsStream(filePath));
        try (Scanner scanner = new Scanner(stream)) {
            StringBuilder next = new StringBuilder();
            int index = 0;
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if (!scanner.hasNextLine()) {
                    // last line
                    next.append(currentLine).append(delimiter());
                    records.add(convertLine(next.toString(), index++));
                } else if (isNextLine(currentLine)) {
                    // line between passports
                    records.add(convertLine(next.toString(), index));
                    next = new StringBuilder();
                } else {
                    // line inside single passport
                    next.append(currentLine).append(delimiter());
                }
            }
        }
        return records;
    }

    T convertLine(String nextLine, int lineNumber);
}
