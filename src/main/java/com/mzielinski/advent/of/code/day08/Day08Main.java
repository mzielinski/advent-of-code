package com.mzielinski.advent.of.code.day08;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day08Main {

    enum Command {
        NOP, ACC, JMP;

        public static Command convert(String command) {
            try {
                return Command.valueOf(command.toUpperCase());
            } catch (Exception e) {
                throw new UnsupportedOperationException("Command " + command + " is not supported");
            }
        }

        @Override
        public String toString() {
            return this.name();
        }
    }

    static class Instruction {

        private final Command command;
        private final int value;

        Instruction(String command, int value) {
            this.command = Command.convert(command);
            this.value = value;
        }

        @Override
        public String toString() {
            return command + " " + value;
        }

        public Instruction revert() {
            switch (command) {
                case NOP:
                    return new Instruction(Command.JMP.name(), value);
                case JMP:
                    return new Instruction(Command.NOP.name(), value);
            }
            return this;
        }
    }

    static class Day08Input implements ReadFile<Instruction> {

        @Override
        public Instruction convertLine(String line, int lineNumber) {
            int value = 0;
            String name = "";
            try (Scanner fi = new Scanner(line)) {
                while (fi.hasNext()) {
                    if (fi.hasNextInt()) {
                        value = fi.nextInt();
                    } else {
                        name = fi.next();
                    }
                }
            }
            return new Instruction(name, value);
        }
    }

    static class Part1Calculator extends Calculator {

        Part1Calculator(List<Instruction> instructions) {
            super(instructions);
        }
    }

    static class Part2Calculator extends Calculator {

        private final int sizeOfAllInstructions;

        Part2Calculator(List<Instruction> instructions) {
            super(instructions);
            this.sizeOfAllInstructions = instructions.size();
        }

        @Override
        public boolean isFinished() {
            if (index == sizeOfAllInstructions) {
                // achieved end of the instructions
                return true;
            } else if (modifiedIndex == sizeOfAllInstructions) {
                throw new RuntimeException("Cannot find any path which will not be finished with infinite loop");
            } else {
                // try to modify next player;
                modifiedIndex++;
                cleanup();
                return false;
            }
        }

        @Override
        public Instruction calculateInstruction() {
            Instruction instruction = super.calculateInstruction();
            return modifiedIndex == index ? instruction.revert() : instruction;
        }
    }

    abstract static class Calculator {

        protected int modifiedIndex = 0;
        protected int index;
        private int accumulator;
        private final List<Integer> passedIndexes = new ArrayList<>();
        private final List<Instruction> instructions;

        protected Calculator(List<Instruction> instructions) {
            this.instructions = instructions;
            cleanup();
        }

        int calculate() {
            while (true) {
                if (outOfRange(instructions.size()) || isInfiniteLoop()) {
                    if (isFinished()) {
                        return accumulator;
                    }
                }
                passedIndexes.add(index);
                Instruction instruction = calculateInstruction();
                switch (instruction.command) {
                    case JMP:
                        index = index + instruction.value;
                        break;
                    case ACC:
                        accumulator += instruction.value;
                    case NOP:
                    default:
                        index++;
                }
            }
        }

        void cleanup() {
            index = 0;
            accumulator = 0;
            passedIndexes.clear();
        }

        boolean isFinished() {
            return true;
        }

        public Instruction calculateInstruction() {
            return instructions.get(index);
        }

        private boolean isInfiniteLoop() {
            return passedIndexes.contains(index);
        }

        private boolean outOfRange(int size) {
            return index >= size;
        }
    }

    private List<Instruction> readFile(String filename) {
        ReadFile<Instruction> file = new Day08Input();
        return file.readFile(filename);
    }

    public static void main(String[] args) {
        Day08Main main = new Day08Main();
        List.of("day08/01.txt", "day08/02.txt").forEach(file -> {
            System.out.println("### File " + file);
            List<Instruction> instructions = main.readFile(file);
            System.out.println("# Result Part1: " + new Day08Main.Part1Calculator(instructions).calculate());
            System.out.println("# Result Part2: " + new Day08Main.Part2Calculator(instructions).calculate());
            System.out.println();
        });
    }
}
