package com.mzielinski.advent.of.code.day12.model;

import java.util.Objects;

public record Position(int north, int south, int east, int west) {

    public static final Position INITIAL = Position.of(0, 0, 0, 0);

    public static Position of(int north, int south, int east, int west) {
        return new Position(north, south, east, west);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return north == position.north
                && south == position.south
                && east == position.east
                && west == position.west;
    }

    @Override
    public int hashCode() {
        return Objects.hash(north, south, east, west);
    }

    public Position changeNorthPosition(int value) {
        return new Position(
                Math.max(north - south + value, 0),
                Math.max(south - value, 0),
                east,
                west);
    }

    public Position changeSouthPosition(int value) {
        return new Position(
                Math.max(north - value, 0),
                Math.max(south - north + value, 0),
                east,
                west);
    }

    public Position changeEastPosition(int value) {
        return new Position(
                north,
                south,
                Math.max(east - west + value, 0),
                Math.max(west - value, 0));
    }

    public Position changeWestPosition(int value) {
        return new Position(
                north,
                south,
                Math.max(east - value, 0),
                Math.max(west - east + value, 0));
    }
}
