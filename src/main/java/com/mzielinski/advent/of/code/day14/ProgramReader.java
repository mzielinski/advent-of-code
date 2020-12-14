package com.mzielinski.advent.of.code.day14;

import com.mzielinski.advent.of.code.utils.AutoPopulatingList;
import com.mzielinski.advent.of.code.utils.ReadFile;

import java.io.InputStream;
import java.util.Scanner;

import static java.util.Objects.requireNonNull;

public class ProgramReader {

    Day14.Memory readFile(String filePath) {
        Day14.Memory memory = new Day14.Memory(new AutoPopulatingList((index, bit) -> bit));
        InputStream stream = requireNonNull(ReadFile.class.getClassLoader().getResourceAsStream(filePath));
        try (Scanner scanner = new Scanner(stream)) {
            String mask = "";
            while (scanner.hasNext()) {
                String next = scanner.next();
                if (next.startsWith("mask")) {
                    scanner.next(); // skip =
                    mask = scanner.next();
                } else if (next.startsWith("mem")) {
                    int address = retrieveAddress(next);
                    scanner.next(); // skip =
                    Day14.Bit bit = new Day14.Bit(Integer.parseInt(scanner.next()), mask.toCharArray());
                    memory.memory().set(address, bit);
                }
            }
        }
        return memory;
    }

    private int retrieveAddress(String memoryAssigment) {
        // regex would be better, but I do not have time to fight with regexps :)
        return Integer.parseInt(memoryAssigment.replaceAll("mem\\[", "")
                .replaceAll("]", ""));
    }

}
