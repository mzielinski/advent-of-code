package com.mzielinski.advent.of.code.day03;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.Arrays;
import java.util.List;

public class Day03Main {

    static class Day03MainPart2Input implements ReadFile<boolean[]> {

        @Override
        public boolean[] getRecordFromLine(String line, int lineNumber) {
            char[] chars = line.toCharArray();
            boolean[] lineTable = new boolean[chars.length];
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '.') {
                    lineTable[i] = true;
                }
            }
            return lineTable;
        }
    }

    private List<boolean[]> readFile(String filename) {
        ReadFile<boolean[]> file = new Day03MainPart2Input();
        return file.readFile(filename);
    }

    private int calculate(List<boolean[]> inputs, int right, int down) {
        int counterFail = 0;
        int j = 0;
        for (int i = 0; i < inputs.size(); i += down) {
            boolean[] line = inputs.get(i);
            counterFail += line[j % line.length] ? 0 : 1;
            j += right;
        }
        return counterFail;
    }

    public static void main(String[] args) {
        Day03Main main = new Day03Main();
        String[] files = new String[]{"day03/01.map", "day03/02.map"};
        Arrays.stream(files).forEach(file -> {
            System.out.println("### File " + file);
            List<boolean[]> inputs = main.readFile(file);
            System.out.println("# Result part1: " + main.calculate(inputs, 3, 1));
            int result = main.calculate(inputs, 1, 1) *
                    main.calculate(inputs, 3, 1) *
                    main.calculate(inputs, 5, 1) *
                    main.calculate(inputs, 7, 1) *
                    main.calculate(inputs, 1, 2);
            System.out.println("# Result part2: " + result);
            System.out.println();
        });
    }
}
