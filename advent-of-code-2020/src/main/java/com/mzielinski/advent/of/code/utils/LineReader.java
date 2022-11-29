package com.mzielinski.advent.of.code.utils;

import java.io.InputStream;
import java.util.Scanner;

import static java.util.Objects.requireNonNull;

public interface LineReader<T> {

    default T readFile(String filePath) {
        InputStream stream = requireNonNull(LineReader.class.getClassLoader().getResourceAsStream(filePath));
        try (Scanner scanner = new Scanner(stream)) {
            return processLines(scanner);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file " + filePath, e);
        }
    }

    T processLines(Scanner scanner);
}
