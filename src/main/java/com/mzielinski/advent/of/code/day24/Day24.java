package com.mzielinski.advent.of.code.day24;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Day24 {

    record Position(int x, int y) {

        private final static List<Position> NEIGHBOUR = List.of(
                new Position(0, 1),   // SE
                new Position(1, 0),   // E
                new Position(1, -1),  // NE
                new Position(-1, 0),  // W
                new Position(-1, 1),  // SW
                new Position(0, -1)); // NW

        public Position move(int changedX, int changedY) {
            return new Position(x + changedX, y + changedY);
        }

        public boolean isNeighbour(Position neighbour) {
            Position referencePosition = move(-neighbour.x, -neighbour.y);
            return NEIGHBOUR.contains(referencePosition);
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

    record Tile(Position position, Color color, List<Direction> directions) {

        Tile changeColor() {
            return new Tile(position, color.revert(), directions);
        }

        Tile recalculatePosition() {
            Position currentPosition = position;
            for (Direction direction : directions) {
                currentPosition = direction.move(currentPosition);
            }
            return new Tile(currentPosition, color, directions);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return Objects.equals(position, tile.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position);
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

    public long countBlackTiles(String filePath) {
        return countBlackTiles(recalculateTiles(filePath));
    }

    public long countBlackTiles(String filePath, int days) {
        Set<Tile> dayTiles = recalculateTiles(filePath);
        return countBlackTileForGivenNumberOfDays(dayTiles, days);
    }

    private long countBlackTileForGivenNumberOfDays(Set<Tile> tiles, int day) {
        Set<Tile> dayTiles = applyDayRules(tiles);
        if (day > 0) {
            return countBlackTileForGivenNumberOfDays(dayTiles, day - 1);
        }
        return countBlackTiles(tiles);
    }

    private Set<Tile> applyDayRules(Set<Tile> tiles) {
        Set<Tile> tilesWithNeighbours = new HashSet<>(tiles);
        tilesWithNeighbours.addAll(createNeighbours(tiles));
        List<Position> tilesMarkedForColorsRevert = tilesWithNeighbours.stream()
                .filter(tile -> markedForChangeColor(tiles, tile))
                .map(Tile::position)
                .collect(toList());

        // return only black tiles
        return tilesWithNeighbours.stream()
                .map(tile -> tilesMarkedForColorsRevert.contains(tile.position()) ? tile.changeColor() : tile)
                .filter(tile -> tile.color() == Color.BLACK)
                .collect(toSet());
    }

    private Set<Tile> createNeighbours(Set<Tile> tiles) {
        return tiles.stream()
                .map(Tile::position)
                .flatMap(this::createNeighbours)
                .collect(toSet());
    }

    private Stream<Tile> createNeighbours(Position position) {
        return Arrays.stream(Direction.values())
                .map(direction -> new Tile(direction.move(position), Color.WHITE, List.of()));
    }

    private long countBlackTiles(Set<Tile> tiles) {
        return tiles.stream()
                .map(Tile::color)
                .filter(color -> color == Color.BLACK)
                .count();
    }

    private long countBlackNeighbours(Position position, Set<Tile> tiles) {
        return tiles.stream()
                .filter(tile -> tile.color() == Color.BLACK)
                .map(Tile::position)
                .filter(tilePosition -> tilePosition.isNeighbour(position))
                .count();
    }

    private Set<Tile> recalculateTiles(String filePath) {
        List<Tile> recalculateTilePositions = new TileReader().readFile(filePath).stream()
                .map(Tile::recalculatePosition)
                .collect(toList());
        return recalculateTilePositions.stream()
                .map(tile -> markedForChangeColor(recalculateTilePositions, tile.position) ? tile.changeColor() : tile)
                .collect(toSet());
    }

    private boolean markedForChangeColor(Set<Tile> tiles, Tile tile) {
        long blackTiles = countBlackNeighbours(tile.position, tiles);
        return switch (tile.color) {
            case BLACK -> blackTiles == 0 || blackTiles > 2;
            case WHITE -> blackTiles == 2;
        };
    }

    private boolean markedForChangeColor(List<Tile> recalculateTiles, Position position) {
        return recalculateTiles.stream()
                .filter(other -> Objects.equals(other.position, position))
                .count() % 2 == 1;
    }
}
