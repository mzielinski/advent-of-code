package com.mzielinski.advent.of.code.day02;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.List;
import java.util.Scanner;

public class Day02MainPart2 {

    static class Day02MainPart2Input implements ReadFile<Input> {

        @Override
        public Input getRecordMultiLines(String line, int lineNumber) {
            Input input = new Input();
            try (Scanner rowScanner = new Scanner(line)) {
                rowScanner.useDelimiter(" ");
                while (rowScanner.hasNext()) {
                    String next = rowScanner.next();
                    if (next.contains("-")) {
                        input.firstIndex = Integer.parseInt(next.split("-")[0]);
                        input.secondIndex = Integer.parseInt(next.split("-")[1]);
                    } else if (next.contains(":")) {
                        input.searchedChar = next.toCharArray()[0];
                    } else {
                        input.password = next;
                    }
                }
            }
            return input;
        }
    }

    private List<Input> readFile() {
        ReadFile<Input> file = new Day02MainPart2Input();
        return file.readFile("day02/02.csv");
    }

    public int calculate(List<Input> inputs) {
        int count = 0;
        for (Input input : inputs) {
            boolean condition = input.countOccurences();
            if (condition) count++;
        }
        System.out.println("Result " + count);
        return count;
    }

    public static void main(String[] args) {
        Day02MainPart2 main = new Day02MainPart2();
        main.calculate(main.readFile());
    }

    static class Input {

        private int firstIndex;
        private int secondIndex;
        private char searchedChar;
        private String password;

        boolean countOccurences() {
            boolean firstCondition = password.length() >= firstIndex && password.charAt(firstIndex - 1) == searchedChar;
            boolean secondCondition = password.length() >= secondIndex && password.charAt(secondIndex - 1) == searchedChar;
            return firstCondition ^ secondCondition;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Input{");
            sb.append("firstIndex=").append(firstIndex);
            sb.append(", secondIndex=").append(secondIndex);
            sb.append(", searchedChar=").append(searchedChar);
            sb.append(", password='").append(password).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}


