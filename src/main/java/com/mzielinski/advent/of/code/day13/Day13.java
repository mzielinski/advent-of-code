package com.mzielinski.advent.of.code.day13;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.util.Objects.requireNonNull;

public class Day13 {

    record Note(BigDecimal timestamp, List<BusId> buses) {
    }

    record BusId(int index, String busId) {

        public boolean isValidId() {
            return busId.matches("[\\d]+");
        }

        public Integer getBusId() {
            return Integer.parseInt(busId);
        }
    }

    static class NoteReader {

        static Note readFile(String filePath) {
            BigDecimal timestamp = BigDecimal.ZERO;
            List<BusId> busIds = new ArrayList<>();
            InputStream stream = requireNonNull(ReadFile.class.getClassLoader().getResourceAsStream(filePath));
            try (Scanner scanner = new Scanner(stream)) {
                while (scanner.hasNextLine()) {
                    if (scanner.hasNextLong()) {
                        timestamp = BigDecimal.valueOf(scanner.nextLong());
                    } else if (scanner.hasNext()) {
                        String[] split = scanner.next().split(",");
                        for (int index = 0; index < split.length; index++) {
                            busIds.add(new BusId(index, split[index]));
                        }
                    }
                }
            }
            return new Note(timestamp, busIds);
        }
    }

    public long findTheEarliestBusPart1(String filePath) {
        Note note = NoteReader.readFile(filePath);
        BigDecimal nextTimestamp = note.timestamp;
        while (true) {
            for (BusId busId : note.buses) {
                if (!busId.isValidId()) continue;
                if (nextTimestamp.longValue() % busId.getBusId() == 0) {
                    final BigDecimal foundTimeStamp = nextTimestamp.subtract(note.timestamp);
                    return foundTimeStamp.multiply(BigDecimal.valueOf(busId.getBusId())).longValue();
                }
            }
            nextTimestamp = nextTimestamp.add(BigDecimal.ONE);
        }
    }

    public long findTheEarliestBusPart2(String filePath) {
        Note note = NoteReader.readFile(filePath);
        BigDecimal nextTimestamp = note.timestamp;
        while (true) {
            BigDecimal finalNextTimestamp = BigDecimal.valueOf(nextTimestamp.longValue());
            System.out.println(finalNextTimestamp);
            final Optional<BusId> bus = note.buses.stream()
                    .filter(BusId::isValidId)
                    .filter(busId -> {
                        final BigDecimal nextTimeStamp = finalNextTimestamp.add(BigDecimal.valueOf(busId.index));
                        return nextTimeStamp.longValue() % busId.getBusId() != 0;
                    })
                    .findAny();
            if (bus.isEmpty()) {
                // found valid sequence
                return finalNextTimestamp.longValue();
            } else {
                nextTimestamp = finalNextTimestamp.add(BigDecimal.ONE);
            }
        }
    }
}
