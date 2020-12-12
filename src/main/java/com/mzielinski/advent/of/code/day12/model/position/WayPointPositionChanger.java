package com.mzielinski.advent.of.code.day12.model.position;

import com.mzielinski.advent.of.code.day12.model.Direction;
import com.mzielinski.advent.of.code.day12.model.Instruction;
import com.mzielinski.advent.of.code.day12.model.Rotation;

import java.util.List;
import java.util.Objects;

import static com.mzielinski.advent.of.code.day12.model.Direction.EAST;

public record WayPointPositionChanger(int north, int south, int east, int west, StandardPositionChanger wayPoint,
                                      Direction direction) implements Position {

    public static final StandardPositionChanger DEFAULT_WAY_POINT = new StandardPositionChanger(1, 0, 10, 0);
    public static final WayPointPositionChanger INITIAL = WayPointPositionChanger.of(0, 0, 0, 0, DEFAULT_WAY_POINT, EAST);

    public static WayPointPositionChanger of(WayPointPositionChanger position, StandardPositionChanger wayPoint, Direction direction) {
        return WayPointPositionChanger.of(position.north, position.south, position.east, position.west, wayPoint, direction);
    }

    public static WayPointPositionChanger of(int north, int south, int east, int west, StandardPositionChanger wayPoint, Direction direction) {
        return new WayPointPositionChanger(north, south, east, west, wayPoint, direction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WayPointPositionChanger position = (WayPointPositionChanger) o;
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
    public WayPointPositionChanger moveForward(Instruction instruction, Direction currentDirection) {
        List<Rotation> rotation = Rotation.findRotation(this.direction, currentDirection);
        StandardPositionChanger wayPointPosition = recalculateWayPointPosition(rotation);
        int north = (wayPointPosition.north() * instruction.value()) + this.north;
        int south = (wayPointPosition.south() * instruction.value()) + this.south;
        int east = (wayPointPosition.east() * instruction.value()) + this.east;
        int west = (wayPointPosition.west() * instruction.value()) + this.west;
        return WayPointPositionChanger.of(
                Math.max(north - south, 0),
                Math.max(south - north, 0),
                Math.max(east - west, 0),
                Math.max(west - east, 0),
                wayPointPosition,
                currentDirection);
    }

    private StandardPositionChanger recalculateWayPointPosition(List<Rotation> rotations) {
        return rotations.stream()
                .map(r -> switch (r) {
                    case R90, L270 -> StandardPositionChanger.of(wayPoint.west(), wayPoint.east(), wayPoint.north(), wayPoint.south());
                    case R180, L180 -> StandardPositionChanger.of(wayPoint.south(), wayPoint.north(), wayPoint.west(), wayPoint.east());
                    case R270, L90 -> StandardPositionChanger.of(wayPoint.east(), wayPoint.west(), wayPoint.south(), wayPoint.north());
                }).findAny()
                .orElse(wayPoint);
    }

    @Override
    public WayPointPositionChanger changeNorthPosition(Instruction instruction, Direction direction) {
        return WayPointPositionChanger.of(this, wayPoint.changeNorthPosition(instruction, direction), direction);
    }

    @Override
    public WayPointPositionChanger changeSouthPosition(Instruction instruction, Direction direction) {
        return WayPointPositionChanger.of(this, wayPoint.changeSouthPosition(instruction, direction), direction);
    }

    @Override
    public WayPointPositionChanger changeEastPosition(Instruction instruction, Direction direction) {
        return WayPointPositionChanger.of(this, wayPoint.changeEastPosition(instruction, direction), direction);
    }

    @Override
    public WayPointPositionChanger changeWestPosition(Instruction instruction, Direction direction) {
        return WayPointPositionChanger.of(this, wayPoint.changeWestPosition(instruction, direction), direction);
    }

    @Override
    public int calculateManhattanDistance() {
        return east() + north() + west() + south();
    }
}
