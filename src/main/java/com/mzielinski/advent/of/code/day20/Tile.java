package com.mzielinski.advent.of.code.day20;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

record Neighbour(int id, DirectionWithReversed direction) {
}

record TileWithPosition(Tile tile, Position position) {

    public int x() {
        return position.x();
    }

    public int y() {
        return position.y();
    }

    @Override
    public String toString() {
        return "id: " + tile.id() + ", position=" + position;
    }
}

record Tile(int id, List<Point> points, Map<String, DirectionWithReversed> boarders) {

    public static final List<Position> MONSTER_CORDS = List.of(
            new Position(0, 18),
            new Position(1, 0),
            new Position(1, 5),
            new Position(1, 6),
            new Position(1, 11),
            new Position(1, 12),
            new Position(1, 17),
            new Position(1, 18),
            new Position(1, 19),
            new Position(2, 1),
            new Position(2, 4),
            new Position(2, 7),
            new Position(2, 10),
            new Position(2, 13),
            new Position(2, 16));

    public static final Map<Direction, BiPredicate<Point, Integer>> boardersConditions = Map.of(
            Direction.UP, ((point, size) -> point.y() == 0),
            Direction.RIGHT, ((point, size) -> point.x() == size),
            Direction.DOWN, ((point, size) -> point.y() == size),
            Direction.LEFT, ((point, size) -> point.x() == 0)
    );

    private static final int MAX_ROTATES = 4;

    public Tile(int id, List<Point> points) {
        this(id, points, Map.of());
    }

    Tile(int id, List<Point> points, Map<String, DirectionWithReversed> boarders) {
        this.id = id;
        this.points = points;
        this.points.sort(Comparator.comparing(Point::y).thenComparing(Point::x));
        this.boarders = calculateBoarders(this);
    }

    public Map<String, DirectionWithReversed> standardBoarders() {
        return boarders().entrySet().stream()
                .filter(boarders -> !boarders.getValue().reversed())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Direction findDirection(String border) {
        return Optional.ofNullable(boarders().get(border))
                .map(DirectionWithReversed::direction)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find common border " + border));
    }

    public Optional<Neighbour> findNeighbour(String border) {
        DirectionWithReversed directionWithReversed = boarders().get(border);
        return Optional.ofNullable(directionWithReversed)
                .map(direction -> new Neighbour(id(), direction));
    }

    public long countCommonBoards(Image image) {
        return image.tiles().stream()
                .filter(points -> points.id() != id())
                .map(Tile::standardBoarders)
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .reduce(0L, (partialResult, boarder) ->
                        boarders().containsKey(boarder) ? partialResult + 1 : partialResult, ($1, $2) -> null);
    }

    public int countMonsters() {
        return tryToFindMonsters(this, MAX_ROTATES)
                .orElseGet(() -> tryToFindMonsters(flip(Direction.RIGHT), MAX_ROTATES)
                        .orElseGet(() -> tryToFindMonsters(flip(Direction.UP), MAX_ROTATES)
                                .orElseThrow(() -> new IllegalArgumentException(
                                        "Cannot find any monster in image"))));
    }

    public int yMax() {
        return points().stream().map(Point::y).max(Integer::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find max x from points"));
    }


    public int xMax() {
        return points().stream().map(Point::x).max(Integer::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find max x from points"));
    }

    public Tile rotate90() {
        return reorganizedTile((y, x) -> x, (y, x) -> yMax() - y);
    }

    public Tile flip(Direction direction) {
        return flip(new DirectionWithReversed(direction, true));
    }

    public Tile flip(DirectionWithReversed directionWithReversed) {
        if (directionWithReversed.reversed()) {
            return switch (directionWithReversed.direction()) {
                case DOWN, UP -> reorganizedTile((y, x) -> y, (y, x) -> xMax() - x);
                case RIGHT, LEFT -> reorganizedTile((y, x) -> yMax() - y, (y, x) -> x);
            };
        }
        return this;
    }

    private Optional<Integer> tryToFindMonsters(Tile tile, int maxRotates) {
        if (maxRotates == 0) {
            return Optional.empty();
        }
        int monsterCounter = 0;
        for (Point point : tile.points()) {
            boolean found = MONSTER_CORDS.stream()
                    .allMatch(monsterPoint -> {
                        int newY = point.y() + monsterPoint.y();
                        int newX = point.x() + monsterPoint.x();
                        return tile.point(newY, newX).filter(Point::isActive).isPresent();
                    });
            if (found) monsterCounter++;
        }
        return monsterCounter == 0 ? tryToFindMonsters(tile.rotate90(), maxRotates - 1) : Optional.of(monsterCounter);
    }

    public int numberOfActivePoints() {
        return points().stream()
                .filter(Point::isActive)
                .mapToInt(point -> 1)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private Tile reorganizedTile(BiFunction<Integer, Integer, Integer> yCalculator,
                                 BiFunction<Integer, Integer, Integer> xCalculator) {
        int ySize = yMax();
        int xSize = xMax();
        List<Point> rotatedTile = new ArrayList<>();
        for (int y = 0; y <= ySize; y++) {
            for (int x = 0; x <= xSize; x++) {
                int y1 = yCalculator.apply(y, x);
                int x1 = xCalculator.apply(y, x);
                point(y, x).ifPresent(value -> rotatedTile.add(new Point(y1, x1, value.value())));
            }
        }
        return new Tile(id(), rotatedTile);
    }

    public Tile rotateToAndFlip(Map<String, DirectionWithReversed> bordersWithDirection) {
        return rotateToAndFlip(this, bordersWithDirection, MAX_ROTATES)
                .orElseGet(() -> rotateToAndFlip(flip(Direction.RIGHT), bordersWithDirection, MAX_ROTATES)
                        .orElseGet(() -> rotateToAndFlip(flip(Direction.UP), bordersWithDirection, MAX_ROTATES)
                                .orElseThrow(() -> new IllegalArgumentException(
                                        "Cannot find matching boarder for tile " + id() + " and neighbours borders " + bordersWithDirection))));
    }

    public Optional<Tile> rotateToAndFlip(Tile tile, Map<String, DirectionWithReversed> bordersWithDirection, int maxRotates) {
        if (maxRotates == 0) {
            return Optional.empty();
        }
        boolean matched = bordersWithDirection.entrySet().stream()
                .allMatch(entry -> {
                    Direction direction = tile.findDirection(entry.getKey());
                    Direction opposite = direction.opposite();
                    DirectionWithReversed value = entry.getValue();
                    return opposite == value.direction() && tile.standardBoarders().containsKey(entry.getKey());
                });
        return matched ? Optional.of(tile) : rotateToAndFlip(tile.rotate90(), bordersWithDirection, maxRotates - 1);
    }

    public Optional<Point> point(int y, int x) {
        return points.stream()
                .filter(point -> point.x() == x && point.y() == y)
                .findAny();
    }

    private Map<String, DirectionWithReversed> calculateBoarders(Tile points) {
        Map<String, DirectionWithReversed> borders = new HashMap<>();
        for (Map.Entry<Direction, BiPredicate<Point, Integer>> entry : boardersConditions.entrySet()) {
            StringBuilder boarder = points.points().stream()
                    .filter(point -> switch (entry.getKey()) {
                        case DOWN -> entry.getValue().test(point, yMax());
                        case RIGHT -> entry.getValue().test(point, xMax());
                        case LEFT, UP -> entry.getValue().test(point, 0);
                    })
                    .reduce(new StringBuilder(), (border, point) -> border.append(point.value()), ($1, $2) -> null);
            borders.put(boarder.toString(), new DirectionWithReversed(entry.getKey(), false));
            borders.put(boarder.reverse().toString(), new DirectionWithReversed(entry.getKey(), true));
        }
        return borders;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        int currentY = 0;
        for (Point point : points()) {
            if (currentY != point.y()) {
                currentY = point.y();
                sb.append("\n");
            }
            sb.append(point.value());
        }
        return sb.toString().trim();
    }
}
