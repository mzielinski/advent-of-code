package com.mzielinski.advent.of.code.day12.model.position;

import com.mzielinski.advent.of.code.day12.model.Direction;
import com.mzielinski.advent.of.code.day12.model.Instruction;

import static com.mzielinski.advent.of.code.day12.model.Direction.*;

public record StandardPositionChanger(int north, int south, int east, int west,
                                      Direction direction) implements Position {

    public static final StandardPositionChanger INITIAL = StandardPositionChanger.of(0, 0, 0, 0, Direction.EAST);

    public static StandardPositionChanger of(int north, int south, int east, int west, Direction direction) {
        return new StandardPositionChanger(north, south, east, west, direction);
    }

    public static StandardPositionChanger of(StandardPositionChanger position, Direction direction) {
        return new StandardPositionChanger(position.north(), position.south(), position.east(), position.west(), direction);
    }

    @Override
    public StandardPositionChanger moveForward(Instruction instruction, Direction direction) {
        return switch (direction) {
            case NORTH -> changeNorthPosition(instruction);
            case SOUTH -> changeSouthPosition(instruction);
            case EAST -> changeEastPosition(instruction);
            case WEST -> changeWestPosition(instruction);
        };
    }

    public StandardPositionChanger changeNorthPosition(Instruction instruction) {
        return new StandardPositionChanger(
                Math.max(north() - south() + instruction.value(), 0),
                Math.max(south() - instruction.value(), 0),
                east(),
                west(),
                direction());
    }

    public StandardPositionChanger changeSouthPosition(Instruction instruction) {
        return new StandardPositionChanger(
                Math.max(north() - instruction.value(), 0),
                Math.max(south() - north() + instruction.value(), 0),
                east(),
                west(),
                direction());
    }

    public StandardPositionChanger changeEastPosition(Instruction instruction) {
        return new StandardPositionChanger(
                north(),
                south(),
                Math.max(east() - west() + instruction.value(), 0),
                Math.max(west() - instruction.value(), 0),
                direction());
    }

    public StandardPositionChanger changeWestPosition(Instruction instruction) {
        return new StandardPositionChanger(
                north(),
                south(),
                Math.max(east() - instruction.value(), 0),
                Math.max(west() - east() + instruction.value(), 0),
                direction());
    }

    @Override
    public Position recalculateDirection(Direction direction, Instruction instruction) {
        final Direction next = switch (direction) {
            case NORTH -> direction.calculateNewDirection(instruction, EAST, SOUTH, WEST);
            case SOUTH -> direction.calculateNewDirection(instruction, WEST, NORTH, EAST);
            case EAST -> direction.calculateNewDirection(instruction, SOUTH, WEST, NORTH);
            case WEST -> direction.calculateNewDirection(instruction, NORTH, EAST, SOUTH);
        };
        return StandardPositionChanger.of(this, next);
    }

    @Override
    public int calculateManhattanDistance() {
        return Math.abs(north() - south()) + Math.abs(east() - west());
    }
}
