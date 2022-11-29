package com.mzielinski.advent.of.code.day13;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public class Day13 {

    record Note(BigDecimal timestamp, List<BusId> buses) {
    }

    record BusId(int index, String busId) {

        private static final Pattern pattern = Pattern.compile("[0-9]+");

        public boolean isValidId() {
            return pattern.matcher(busId).matches();
        }

        public Integer id() {
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
                if (nextTimestamp.longValue() % busId.id() == 0) {
                    final BigDecimal foundTimeStamp = nextTimestamp.subtract(note.timestamp);
                    return foundTimeStamp.multiply(BigDecimal.valueOf(busId.id())).longValue();
                }
            }
            nextTimestamp = nextTimestamp.add(BigDecimal.ONE);
        }
    }

    public long findTheEarliestBusPart2(String filePath) {
        Note note = NoteReader.readFile(filePath);
        long product = note.buses.stream()
                .filter(BusId::isValidId)
                .mapToLong(BusId::id)
                .reduce((a, b) -> a * b)
                .orElse(0L);
        long sum = note.buses.stream()
                .filter(BusId::isValidId)
                .mapToLong(bus -> bus.index * (product / bus.id()) * inverseModulo(product / bus.id(), bus.id()))
                .sum();
        return product - sum % product;
    }

    // Math concept of "inverse modulo". It is able to calculate result in quick time, much more quicker
    // than brute-force with ```for``` loop.
    // I found this concept in the Internet. I would have never solved this puzzle by my self without this suggestion.
    // I finished studies too long time ago ¯\__( ツ )__/¯
    long inverseModulo(long x, long y) {
        if (x != 0) {
            long modulo = y % x;
            return modulo == 0 ? 1 : y - inverseModulo(modulo, x) * y / x;
        }
        return 0;
    }
}
