package com.mzielinski.advent.of.code.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Objects.requireNonNull;

public interface ReadFile<T> {

    default List<T> readFile(String filePath) {
        List<T> records = new ArrayList<>();
        InputStream stream = requireNonNull(ReadFile.class.getClassLoader().getResourceAsStream(filePath));
        try (Scanner scanner = new Scanner(stream)) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        }
        return records;
    }

    T getRecordFromLine(String nextLine);
}
