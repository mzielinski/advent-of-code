package com.mzielinski.advent.of.code.day12.model.position;

import com.mzielinski.advent.of.code.day12.model.Direction;
import com.mzielinski.advent.of.code.day12.model.Instruction;
import com.mzielinski.advent.of.code.day12.model.Rotation;

import static com.mzielinski.advent.of.code.day12.model.Direction.EAST;

public record WayPointPositionChanger(int north, int south, int east, int west,
                                      StandardPositionChanger wayPoint) implements Position {

    public static final StandardPositionChanger DEFAULT_WAY_POINT = new StandardPositionChanger(1, 0, 10, 0, EAST);
    public static final WayPointPositionChanger INITIAL = WayPointPositionChanger.of(0, 0, 0, 0, DEFAULT_WAY_POINT);

    public static WayPointPositionChanger of(WayPointPositionChanger position, StandardPositionChanger wayPoint) {
        return WayPointPositionChanger.of(position.north, position.south, position.east, position.west, wayPoint);
    }

    public static WayPointPositionChanger of(int north, int south, int east, int west, StandardPositionChanger wayPoint) {
        return new WayPointPositionChanger(north, south, east, west, wayPoint);
    }

    @Override
    public WayPointPositionChanger moveForward(Instruction instruction, Direction currentDirection) {
        int north = (wayPoint.north() * instruction.value()) + this.north;
        int south = (wayPoint.south() * instruction.value()) + this.south;
        int east = (wayPoint.east() * instruction.value()) + this.east;
        int west = (wayPoint.west() * instruction.value()) + this.west;
        return WayPointPositionChanger.of(
                Math.max(north - south, 0),
                Math.max(south - north, 0),
                Math.max(east - west, 0),
                Math.max(west - east, 0),
                wayPoint);
    }

    @Override
    public WayPointPositionChanger changeNorthPosition(Instruction instruction) {
        return WayPointPositionChanger.of(this, wayPoint.changeNorthPosition(instruction));
    }

    @Override
    public WayPointPositionChanger changeSouthPosition(Instruction instruction) {
        return WayPointPositionChanger.of(this, wayPoint.changeSouthPosition(instruction));
    }

    public WayPointPositionChanger changeEastPosition(Instruction instruction) {
        return WayPointPositionChanger.of(this, wayPoint.changeEastPosition(instruction));
    }

    @Override
    public WayPointPositionChanger changeWestPosition(Instruction instruction) {
        return WayPointPositionChanger.of(this, wayPoint.changeWestPosition(instruction));
    }

    @Override
    public int calculateManhattanDistance() {
        return Math.abs(north() - south()) + Math.abs(east() - west());
    }

    @Override
    public Direction direction() {
        return wayPoint.direction();
    }

    @Override
    public Position recalculateDirection(Direction direction, Instruction instruction) {
        Position position = wayPoint.recalculateDirection(direction, instruction);
        StandardPositionChanger modifiedWayPoint = Rotation.findRotation(direction, position.direction()).stream()
                .map(r -> {
                    int west = wayPoint.west();
                    int east = wayPoint.east();
                    int north = wayPoint.north();
                    int south = wayPoint.south();
                    return switch (r) {
                        case R90, L270 -> StandardPositionChanger.of(west, east, north, south, position.direction());
                        case R180, L180 -> StandardPositionChanger.of(south, north, west, east, position.direction());
                        case R270, L90 -> StandardPositionChanger.of(east, west, south, north, position.direction());
                    };
                }).findAny()
                .orElse(StandardPositionChanger.of(wayPoint, position.direction()));
        return WayPointPositionChanger.of(this, modifiedWayPoint);
    }
}
