import utils.LineReader;

import java.util.*;
import java.util.stream.LongStream;

public class Day09 {

    static class Day09InputPart1 implements LineReader<Long> {

        private final int preamble;

        Day09InputPart1(int preamble) {
            this.preamble = preamble;
        }

        // support for huge files. Put into memory max preamble size from file
        @Override
        public Long processLines(Scanner scanner) {
            int index = 0;
            Queue<Long> buffer = new ArrayDeque<>();
            while (scanner.hasNextLong()) {
                long currentNumber = scanner.nextLong();
                if (++index > preamble) {
                    if (isInvalidSequence(buffer, currentNumber)) {
                        return currentNumber;
                    }
                    buffer.poll();
                }
                buffer.add(currentNumber);
            }
            return null;
        }
    }

    static class Day09InputPart2 implements LineReader<Long> {

        private final long searchingSum;

        Day09InputPart2(long searchingSum) {
            this.searchingSum = searchingSum;
        }

        // support for huge files. Put into memory temporary sequence size
        // where sum of all elements cannot be higher then searching sum
        @Override
        public Long processLines(Scanner scanner) {
            Queue<Long> buffer = new ArrayDeque<>();
            while (scanner.hasNextLong()) {
                LongSummaryStatistics stats = findValidSequence(buffer, searchingSum);
                if (stats != null) {
                    return stats.getMin() + stats.getMax();
                } else if (convertToLongStream(buffer).sum() > searchingSum) {
                    // try with next sequence. Remove first element from the bottom
                    buffer.poll();
                }
                // add new on the top of the queue
                buffer.add(scanner.nextLong());
            }
            return null;
        }
    }

    private static LongSummaryStatistics findValidSequence(Queue<Long> buffer, long searchingSum) {
        Queue<Long> queue = new ArrayDeque<>(buffer);
        for (long ignored : queue) {
            queue.poll();
            if (convertToLongStream(queue).sum() == searchingSum) {
                // valid sequence has been found
                return convertToLongStream(queue).summaryStatistics();
            }
        }
        return null;
    }

    private static LongStream convertToLongStream(Collection<Long> collection) {
        return collection.stream().mapToLong(a -> a);
    }

    private static boolean isInvalidSequence(Queue<Long> buffer, long expectedSum) {
        // support 0(n) with Map hashes
        Map<Long, Long> pairs = new HashMap<>();
        for (Long number : buffer) {
            if (pairs.containsKey(expectedSum - number)) {
                // expected sum was found. Valid sequence
                return false;
            }
            pairs.put(number, number);
        }
        return true;
    }

    // part 1
    Long findFirstInvalidNumber(String filePath, int preamble) {
        return new Day09InputPart1(preamble).readFile(filePath);
    }

    // part 2
    Long findEncryptionWeakness(String filePath, long searchingSum) {
        return new Day09InputPart2(searchingSum).readFile(filePath);
    }
}
