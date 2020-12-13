package com.mzielinski.advent.of.code.day12.model;

import com.mzielinski.advent.of.code.day12.Day12.FerryMap;
import com.mzielinski.advent.of.code.day12.model.position.Position;

public record Instruction(Command command, int value) {

    public static Instruction parse(String line) {
        Command command = Command.valueOf(line.substring(0, 1));
        int value = Integer.parseInt(line.substring(1));
        return new Instruction(command, value);
    }

    @Override
    public String toString() {
        return "Command: " + command + ", value: " + value;
    }

    public FerryMap<Position> calculatePosition(FerryMap<? extends Position> map) {
        return new FerryMap<>(map.getPosition()
                .recalculateDirection(map.getPosition().direction(), this)
                .recalculatePosition(this));
    }
}
