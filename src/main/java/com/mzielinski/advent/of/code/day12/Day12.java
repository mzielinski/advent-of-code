package com.mzielinski.advent.of.code.day12;

import com.mzielinski.advent.of.code.day12.input.InstructionsReader;
import com.mzielinski.advent.of.code.day12.model.Direction;
import com.mzielinski.advent.of.code.day12.model.Instruction;
import com.mzielinski.advent.of.code.day12.model.position.Position;
import com.mzielinski.advent.of.code.day12.model.position.StandardPositionChanger;
import com.mzielinski.advent.of.code.day12.model.position.WayPointPositionChanger;

import java.util.stream.Stream;

public record Day12(FerryMap<Position> ferryMap) {

    public static class FerryMap<T extends Position> {

        private final T position;
        private final Direction direction;

        public FerryMap(T position) {
            this(position, Direction.EAST);
        }

        public FerryMap(T position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }

        public FerryMap<Position> applyInstruction(Instruction instruction) {
            return instruction.calculatePosition(this);
        }

        int calculateManhattanDistance() {
            return position.calculateManhattanDistance();
        }

        public T getPosition() {
            return position;
        }

        public Direction getDirection() {
            return direction;
        }

        @Override
        public String toString() {
            return "Position: " + position + ", direction: " + direction;
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
