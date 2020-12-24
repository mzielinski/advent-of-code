package com.mzielinski.advent.of.code.day20;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record Tile(int id, List<Point> tile, Map<String, DirectionWithReversed> boarders) {

    private static final Map<Direction, BiPredicate<Point, Integer>> boardersConditions = Map.of(
            Direction.NORTH, ((point, size) -> point.y() == 0),
            Direction.EAST, ((point, size) -> point.x() == size - 1),
            Direction.SOUTH, ((point, size) -> point.y() == size - 1),
            Direction.WEST, ((point, size) -> point.x() == 0)
    );

    record DirectionWithReversed(Direction direction, boolean reversed) {
    }

    enum Direction {
        NORTH, EAST, SOUTH, WEST;
    }

    public Tile(int id, List<Point> tile) {
        this(id, tile, Map.of());
    }

    Tile(int id, List<Point> tile, Map<String, DirectionWithReversed> boarders) {
        this.id = id;
        this.tile = tile;
        this.tile.sort(Comparator.comparing(Point::y).thenComparing(Point::x));
        this.boarders = calculateBoarders(this.tile);
    }

    public long countCommonBoards(List<Tile> tiles) {
        return tiles.stream()
                .filter(tile -> tile.id() != id())
                .map(Tile::boarders)
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .reduce(0L, (partialResult, boarder) ->
                        boarders().containsKey(boarder) ? partialResult + 1 : partialResult, ($1, $2) -> null);
    }

    public int size() {
        return (int) Math.sqrt(tile.size());
    }

    public Tile rotate90() {
        int size = size();
        List<Point> rotatedTile = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                rotatedTile.add(new Point(size - 1 - y, x, point(y, x).value()));
            }
        }
        return new Tile(id(), rotatedTile);
    }

    public Point point(int y, int x) {
        return tile.stream()
                .filter(point -> point.x() == x && point.y() == y)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find point y: " + y + ", x: " + x));
    }

    private Map<String, DirectionWithReversed> calculateBoarders(List<Point> tile) {
        Map<String, DirectionWithReversed> borders = new HashMap<>();
        for (Map.Entry<Direction, BiPredicate<Point, Integer>> entry : boardersConditions.entrySet()) {
            StringBuilder boarder = tile.stream()
                    .filter(point -> entry.getValue().test(point, size()))
                    .reduce(new StringBuilder(), (border, point) -> border.append(point.value()), ($1, $2) -> null);
            borders.put(boarder.toString(), new DirectionWithReversed(entry.getKey(), false));
            borders.put(boarder.reverse().toString(), new DirectionWithReversed(entry.getKey(), true));
        }
        return borders;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        double length = Math.sqrt(tile.size());
        tile.forEach(point -> {
            sb.append(point.value());
            if (point.x() == length - 1) sb.append("\n");
        });
        return sb.toString();
    }
}
