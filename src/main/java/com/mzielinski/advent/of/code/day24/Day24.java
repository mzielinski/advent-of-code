package com.mzielinski.advent.of.code.day24;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class Day24 {

    record Position(int x, int y) {

        public Position move(int changedX, int changedY) {
            return new Position(x + changedX, y + changedY);
        }
    }

    enum Color {
        WHITE {
            @Override
            Color revert() {
                return BLACK;
            }
        }, BLACK {
            @Override
            Color revert() {
                return WHITE;
            }
        };

        abstract Color revert();
    }

    record Tile(int id, Position position, Color color, List<Direction> directions) {

        Tile changeColor() {
            return new Tile(id, position, color.revert(), directions);
        }

        Tile recalculatePosition() {
            Position currentPosition = position;
            for (Direction direction : directions) {
                currentPosition = direction.move(currentPosition);
            }
            return new Tile(id, currentPosition, color, directions);
        }
    }

    enum Direction {
        EAST("e") {
            @Override
            Position move(Position current) {
                return current.move(1, 0);
            }
        }, SOUTH_EAST("se") {
            @Override
            Position move(Position current) {
                return current.move(0, 1);
            }
        }, NORTH_EAST("ne") {
            @Override
            Position move(Position current) {
                return current.move(1, -1);
            }
        }, WEST("w") {
            @Override
            Position move(Position current) {
                return current.move(-1, 0);
            }
        }, SOUTH_WEST("sw") {
            @Override
            Position move(Position current) {
                return current.move(-1, 1);
            }
        }, NORTH_WEST("nw") {
            @Override
            Position move(Position current) {
                return current.move(0, -1);
            }
        };

        private final String direction;

        Direction(String direction) {
            this.direction = direction;
        }

        static Direction parseTo(String direction) {
            return Arrays.stream(values())
                    .filter(e -> Objects.equals(e.direction, direction))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("Provided direction " + direction + " is not supported"));
        }

        abstract Position move(Position current);
    }

    public List<String> checkColor(String filePath) {
        return recalculateTiles(filePath).stream()
                .map(Tile::color)
                .map(Enum::toString)
                .collect(toList());
    }

    private List<Tile> recalculateTiles(String filePath) {
        List<Tile> recalculateTilePositions = new TileReader().readFile(filePath).stream()
                .map(Tile::recalculatePosition)
                .collect(toList());
        return recalculateTilePositions.stream()
                .filter(tile -> countOccurrences(recalculateTilePositions, tile.position) % 2 == 1)
                .map(Tile::changeColor)
                .collect(toList());
    }

    private long countOccurrences(List<Tile> recalculateTiles, Position position) {
        return recalculateTiles.stream()
                .filter(other -> Objects.equals(other.position, position))
                .count();
    }
}
