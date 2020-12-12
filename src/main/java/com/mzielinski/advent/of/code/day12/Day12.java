package com.mzielinski.advent.of.code.day12;

import com.mzielinski.advent.of.code.day12.input.InstructionsReader;
import com.mzielinski.advent.of.code.day12.model.Direction;
import com.mzielinski.advent.of.code.day12.model.Instruction;
import com.mzielinski.advent.of.code.day12.model.Position;

import java.util.stream.Stream;

public class Day12 {

    public static class FerryMap {

        private final Position position;
        private final Direction direction;

        public FerryMap() {
            this.position = Position.INITIAL;
            this.direction = Direction.EAST;
        }

        public FerryMap(Position position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }

        public FerryMap applyInstruction(Instruction instruction) {
            return instruction.calculatePosition(this);
        }

        int calculateManhattanDistance() {
            return position.east() + position.north() + position.west() + position.south();
        }

        public Position getPosition() {
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

    private final FerryMap map = new FerryMap();

    public FerryMap calculatePositionsOnMap(String filePath) {
        try (InstructionsReader instructions = new InstructionsReader(filePath)) {
            Stream<Instruction> stream = instructions.getStream();
            return stream.reduce(map, FerryMap::applyInstruction, ($1, $2) -> null);
        }
    }
}
