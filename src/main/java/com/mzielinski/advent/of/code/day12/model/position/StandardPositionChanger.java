package com.mzielinski.advent.of.code.day12.model.position;

import com.mzielinski.advent.of.code.day12.model.Direction;
import com.mzielinski.advent.of.code.day12.model.Instruction;

import java.util.Objects;

public record StandardPositionChanger(int north, int south, int east, int west) implements Position {

    public static final StandardPositionChanger INITIAL = StandardPositionChanger.of(0, 0, 0, 0);

    public static StandardPositionChanger of(int north, int south, int east, int west) {
        return new StandardPositionChanger(north, south, east, west);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardPositionChanger position = (StandardPositionChanger) o;
        return north == position.north
                && south == position.south
                && east == position.east
                && west == position.west;
    }

    @Override
    public int hashCode() {
        return Objects.hash(north, south, east, west);
    }

    @Override
    public StandardPositionChanger moveForward(Instruction instruction, Direction direction) {
        return switch (direction) {
            case NORTH -> changeNorthPosition(instruction, direction);
            case SOUTH -> changeSouthPosition(instruction, direction);
            case EAST -> changeEastPosition(instruction, direction);
            case WEST -> changeWestPosition(instruction, direction);
        };
    }

    public StandardPositionChanger changeNorthPosition(Instruction instruction, Direction direction) {
        return new StandardPositionChanger(
                Math.max(north - south + instruction.value(), 0),
                Math.max(south - instruction.value(), 0),
                east,
                west);
    }

    public StandardPositionChanger changeSouthPosition(Instruction instruction, Direction direction) {
        return new StandardPositionChanger(
                Math.max(north - instruction.value(), 0),
                Math.max(south - north + instruction.value(), 0),
                east,
                west);
    }

    public StandardPositionChanger changeEastPosition(Instruction instruction, Direction direction) {
        return new StandardPositionChanger(
                north,
                south,
                Math.max(east - west + instruction.value(), 0),
                Math.max(west - instruction.value(), 0));
    }

    public StandardPositionChanger changeWestPosition(Instruction instruction, Direction direction) {
        return new StandardPositionChanger(
                north,
                south,
                Math.max(east - instruction.value(), 0),
                Math.max(west - east + instruction.value(), 0));
    }

    @Override
    public int calculateManhattanDistance() {
        return east() + north() + west() + south();
    }
}
