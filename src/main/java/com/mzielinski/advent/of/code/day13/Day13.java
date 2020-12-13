package com.mzielinski.advent.of.code.day13;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

public class Day13 {

    record Note(long timestamp, Set<Integer> buses) {
    }

    record Result(long waitTime, Integer busId) {
    }

    static class NoteReader {

        static Note readFile(String filePath) {
            long timestamp = 0;
            Set<Integer> busIds = new HashSet<>();
            InputStream stream = requireNonNull(ReadFile.class.getClassLoader().getResourceAsStream(filePath));
            try (Scanner scanner = new Scanner(stream)) {
                while (scanner.hasNextLine()) {
                    if (scanner.hasNextLong()) {
                        timestamp = scanner.nextLong();
                    } else if (scanner.hasNext()) {
                        busIds = Arrays.stream(scanner
                                .next()
                                .split(","))
                                .filter(a -> a.matches("[\\d]+"))
                                .map(Integer::parseInt)
                                .collect(toSet());
                    }
                }
            }
            return new Note(timestamp, busIds);
        }
    }

    public long findTheEarliestBus(String filePath) {
        Note note = NoteReader.readFile(filePath);
        Result result = extracted(note);
        return result.waitTime * result.busId;
    }

    private Result extracted(Note note) {
        long index = note.timestamp;
        while (true) {
            index++;
            for (Integer busId : note.buses) {
                if (index % busId == 0) {
                    return new Result(index - note.timestamp, busId);
                }
            }
        }
    }
}
