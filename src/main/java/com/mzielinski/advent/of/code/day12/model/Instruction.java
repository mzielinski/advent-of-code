package com.mzielinski.advent.of.code.day12.model;

import com.mzielinski.advent.of.code.day12.model.position.Position;

public record Instruction(Command command, int value) {

    public static Instruction parse(String line) {
        Command command = Command.valueOf(line.substring(0, 1));
        int value = Integer.parseInt(line.substring(1));
        return new Instruction(command, value);
    }

    public Ferry<Position> calculatePosition(Position position) {
        Position newPosition = position
                .recalculateDirection(position.direction(), this)
                .recalculatePosition(this);
        return new Ferry<>(newPosition);
    }

    @Override
    public String toString() {
        return "Command: " + command + ", value: " + value;
    }
}
