package com.mzielinski.advent.of.code.day12;

import com.mzielinski.advent.of.code.day12.input.InstructionsReader;
import com.mzielinski.advent.of.code.day12.model.Instruction;
import com.mzielinski.advent.of.code.day12.model.position.Position;
import com.mzielinski.advent.of.code.day12.model.position.StandardPositionChanger;
import com.mzielinski.advent.of.code.day12.model.position.WayPointPositionChanger;

import java.util.stream.Stream;

public record Day12(FerryMap<Position> ferryMap) {

    public static record FerryMap<T extends Position>(T position) {

        public FerryMap<Position> applyInstruction(Instruction instruction) {
            return instruction.calculatePosition(this);
        }

        int calculateManhattanDistance() {
            return position.calculateManhattanDistance();
        }

        public T getPosition() {
            return position;
        }

        @Override
        public String toString() {
            return "Position: " + position;
        }
    }

    public static Day12 part1() {
        return new Day12(new FerryMap<>(StandardPositionChanger.INITIAL));
    }

    public static Day12 part2() {
        return new Day12(new FerryMap<>(WayPointPositionChanger.INITIAL));
    }

    public FerryMap<? extends Position> calculatePositionsOnMap(String filePath) {
        try (InstructionsReader instructions = new InstructionsReader(filePath)) {
            Stream<Instruction> stream = instructions.getStream();
            return stream.reduce(ferryMap, FerryMap::applyInstruction, ($1, $2) -> null);
        }
    }
}
