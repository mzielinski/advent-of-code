package com.mzielinski.advent.of.code.day12.model;

import com.mzielinski.advent.of.code.day12.model.position.Position;

public record Ferry<T extends Position>(T position) {

    public Ferry<Position> applyInstruction(Instruction instruction) {
        return instruction.calculatePosition(position);
    }

    public int calculateManhattanDistance() {
        return position.calculateManhattanDistance();
    }

    @Override
    public String toString() {
        return "Position: " + position;
    }
}
