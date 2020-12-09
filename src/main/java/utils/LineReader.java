package utils;

import java.util.Scanner;

public interface LineReader<T> {

    default T readFile(String filePath) {
        try (Scanner scanner = new Scanner(this.getClass().getResourceAsStream(filePath))) {
            return processLines(scanner);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file " + filePath, e);
        }
    }

    T processLines(Scanner scanner);
}
