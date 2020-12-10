package com.mzielinski.advent.of.code.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Objects.requireNonNull;

public interface MultilineReadFile<T> extends ReadFile<T> {

    String DELIMITER = ";";

    default List<T> readFile(String filePath) {
        List<T> records = new ArrayList<>();
        InputStream stream = requireNonNull(MultilineReadFile.class.getClassLoader().getResourceAsStream(filePath));
        try (Scanner scanner = new Scanner(stream)) {
            StringBuilder next = new StringBuilder();
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if (!scanner.hasNextLine()) {
                    // last line
                    next.append(currentLine).append(DELIMITER);
                    records.add(getRecordFromLine(next.toString()));
                } else if ("".equals(currentLine)) {
                    // line between passports
                    records.add(getRecordFromLine(next.toString()));
                    next = new StringBuilder();
                } else {
                    // line inside single passport
                    next.append(currentLine).append(DELIMITER);
                }
            }
        }
        return records;
    }

    T getRecordFromLine(String nextLine);
}
