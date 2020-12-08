import utils.ReadFile;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Day05Main {

    private enum OPERATION {
        LOW('F', 'L'), UP('B', 'R');

        private final Set<Character> supportedChars;

        OPERATION(Character... supportedChars) {
            this.supportedChars = new HashSet<>(Arrays.asList(supportedChars));
        }

        static OPERATION get(Character c) {
            if (LOW.supportedChars.contains(c)) return LOW;
            else if (UP.supportedChars.contains(c)) return UP;
            throw new RuntimeException("Not support character " + c);
        }
    }

    private static class Seat {

        private final char[] row = new char[7];
        private final char[] column = new char[3];

        void addRow(int index, char aChar) {
            row[index] = aChar;
        }

        void addColumn(int index, char aChar) {
            column[index] = aChar;
        }

        long calculateSeatId() {
            long calculatedRow = calculateRowOrColumn(row, 127);
            long calculatedColumn = calculateRowOrColumn(column, 7);
            return calculatedRow * 8 + calculatedColumn;
        }

        long calculateRowOrColumn(char[] table, int maxValue) {
            long min = 0;
            long max = maxValue;
            for (char c : table) {
                long result = (max - min) / 2;
                switch (OPERATION.get(c)) {
                    case UP:
                        min = min + result + 1;
                        break;
                    case LOW:
                        max = min + result;
                        break;
                }
            }
            if (min != max) {
                throw new IllegalStateException("Min " + min + " and Max " + max + " should be the same");
            }
            return min;
        }
    }

    static class Day05Input implements ReadFile<Seat> {

        @Override
        public Seat getRecordFromLine(String line) {
            Seat seat = new Seat();
            char[] chars = line.toCharArray();
            if (chars.length != 10) {
                System.err.println("Invalid line " + line);
            }

            for (int i = 0; i < 7; i++) {
                seat.addRow(i, chars[i]);
            }
            for (int i = 0; i < 3; i++) {
                seat.addColumn(i, chars[i + 7]);
            }
            return seat;
        }
    }

    private List<Seat> readFile(String filename) {
        ReadFile<Seat> file = new Day05Input();
        return file.readFile(filename);
    }

    private long calculateMax(List<Seat> seats) {
        return seats.stream()
                .map(Seat::calculateSeatId)
                .max(Long::compare)
                .orElseThrow(() -> new RuntimeException("Unable to compute max value"));
    }

    private List<Long> findMySeatId(List<Seat> seats) {
        List<Long> allIds = seats.stream()
                .map(Seat::calculateSeatId)
                .sorted()
                .collect(toList());
        return findMissingNumbers(allIds);
    }

    private List<Long> findMissingNumbers(List<Long> ids) {
        List<Long> foundNumbers = new ArrayList<>();
        long previousNumber = ids.get(0);
        for (int i = 1; i < ids.size(); i++) {
            long currentNumber = ids.get(i);
            long expectedNumber = previousNumber + 1;
            if (expectedNumber != currentNumber) {
                foundNumbers.add(expectedNumber);
            }
            previousNumber = currentNumber;
        }
        return foundNumbers;
    }

    public static void main(String[] args) {
        Day05Main main = new Day05Main();
        String[] files = new String[]{"day05/00.seat", "day05/01.seat", "day05/02.seat"};
        Arrays.stream(files).forEach(file -> {
            System.out.println("### File " + file);
            List<Seat> seats = main.readFile(file);
            System.out.println("# Result Part 1: " + main.calculateMax(seats));
            System.out.println("# Result Part 2: " + main.findMySeatId(seats));
            System.out.println();
        });
    }
}
