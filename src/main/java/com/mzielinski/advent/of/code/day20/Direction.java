package com.mzielinski.advent.of.code.day20;

record DirectionWithReversed(Direction direction, boolean reversed) {
}

enum Direction {
    UP {
        @Override
        Direction opposite() {
            return DOWN;
        }

        @Override
        Position position(Position position) {
            return new Position(position.y() - 1, position.x());
        }
    }, RIGHT {
        @Override
        Direction opposite() {
            return LEFT;
        }

        @Override
        Position position(Position position) {
            return new Position(position.y(), position.x() + 1);
        }
    }, DOWN {
        @Override
        Direction opposite() {
            return UP;
        }

        @Override
        Position position(Position position) {
            return new Position(position.y() + 1, position.x());
        }
    }, LEFT {
        @Override
        Direction opposite() {
            return RIGHT;
        }

        @Override
        Position position(Position position) {
            return new Position(position.y(), position.x() - 1);
        }
    };

    abstract Direction opposite();

    abstract Position position(Position position);
}