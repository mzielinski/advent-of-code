package com.mzielinski.advent.of.code.day25;

public class Day25 {

    record KeyResolver(long loopSize) {

        public static KeyResolver findLoopSize(long subjectNumber, long expectedKeyValue, long initLoop) {
            long current = 1;
            for (long i = initLoop; true; i++) {
                current = transformer(subjectNumber, current);
                if (current == expectedKeyValue) return new KeyResolver(i + 1);
            }
        }

        public long keyValue(long subjectNumber) {
            long current = 1;
            for (long i = 0; i < loopSize; i++) {
                current = transformer(subjectNumber, current);
            }
            return current;
        }

        private static long transformer(long subjectNumber, long current) {
            current *= subjectNumber;
            current %= 20201227L;
            return current;
        }
    }

    public long findEncryptionKey(long cardPublicKey, long doorPublicKey) {
        if (cardPublicKey < doorPublicKey) {
            return KeyResolver.findLoopSize(7, cardPublicKey, 0).keyValue(doorPublicKey);
        }
        return KeyResolver.findLoopSize(7, doorPublicKey, 0).keyValue(cardPublicKey);
    }
}
