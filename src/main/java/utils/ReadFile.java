package utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface ReadFile<T> {

    default List<T> readFile(String filePath) {
        List<T> records = new ArrayList<>();
        InputStream resourceAsStream = getClass().getResourceAsStream(filePath);
        if (resourceAsStream == null) return List.of();
        try (Scanner scanner = new Scanner(resourceAsStream)) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        }
        return records;
    }

    T getRecordFromLine(String nextLine);
}
