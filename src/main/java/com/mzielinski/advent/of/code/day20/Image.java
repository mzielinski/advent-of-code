package com.mzielinski.advent.of.code.day20;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record Image(List<Tile> tiles, List<TileWithPosition> sortedTiles) {

    public Image(List<Tile> tiles) {
        this(tiles, List.of());
    }

    Image(List<Tile> tiles, List<TileWithPosition> sortedTiles) {
        this.tiles = tiles;
        this.sortedTiles = sortedTiles;
        if (!this.sortedTiles.isEmpty()) {
            this.sortedTiles.sort(Comparator.comparing(TileWithPosition::y).thenComparing(TileWithPosition::x));
        }
    }

    public Stream<Neighbour> findNeighbours(int id, String boarder) {
        return tiles().parallelStream()
                .filter(tile -> tile.id() != id)
                .map(tile -> tile.findNeighbour(boarder))
                .filter(Optional::isPresent)
                .flatMap(Optional::stream);
    }

    public Image createImage() {
        Map<Integer, TileWithPosition> image = new HashMap<>();
        Set<Integer> lastNeighbours = new HashSet<>();
        while (image.size() != tiles.size()) {
            tiles.parallelStream()
                    .filter(tile -> tryToFindNextTile(image, lastNeighbours, tile))
                    .map(tile -> image.getOrDefault(tile.id(), new TileWithPosition(tile, new Position(0, 0))))
                    .findAny()
                    .ifPresent(tileWithPosition -> {
                                Tile tile = tileWithPosition.tile();
                                Map<Integer, TileWithPosition> neighbours = findNeighbours(tile, image)
                                        .map(neighbour -> rotateAndFlipNeighbour(tileWithPosition, neighbour, image))
                                        .collect(Collectors.toMap(neighbour -> neighbour.tile().id(), Function.identity()));

                                image.put(tile.id(), tileWithPosition);
                                image.putAll(neighbours);

                                // save last neighbours
                                lastNeighbours.clear();
                                lastNeighbours.addAll(neighbours.keySet());
                            }
                    );
        }
        return new Image(tiles(), new ArrayList<>(image.values()));
    }

    private Stream<Neighbour> findNeighbours(Tile tile, Map<Integer, TileWithPosition> image) {
        return tile.standardBoarders().entrySet().parallelStream()
                .flatMap(entry -> findNeighbours(tile.id(), entry.getKey()))
                .filter(neighbour -> !image.containsKey(neighbour.id()));
    }

    private boolean tryToFindNextTile(Map<Integer, TileWithPosition> image,
                                      Set<Integer> lastNeighbours,
                                      Tile tile) {
        if (image.isEmpty()) return true;
        else if (lastNeighbours.contains(tile.id()) && image.containsKey(tile.id())) return true;
        return lastNeighbours.isEmpty() && image.containsKey(tile.id()) && findNeighbours(tile, image).findAny().isPresent();
    }

    private TileWithPosition rotateAndFlipNeighbour(TileWithPosition tile, Neighbour neighbourId, Map<Integer, TileWithPosition> alreadyFittedTiles) {
        Tile neighbour = findTile(neighbourId.id()).flip(neighbourId.direction());
        Map<String, DirectionWithReversed> boarders = neighbour.standardBoarders();

        // search common borders for all tiles already rotated
        Map<String, DirectionWithReversed> allBorders = alreadyFittedTiles.values().parallelStream()
                .map(TileWithPosition::tile)
                .map(Tile::standardBoarders)
                .flatMap(entry -> entry.entrySet().parallelStream())
                .filter(entry -> boarders.containsKey(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // main tile
        String border = tile.tile().standardBoarders().keySet().parallelStream()
                .filter(boarders::containsKey)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find common border tile " + tile.tile().id() + " and neighbour " + neighbourId.id()));
        DirectionWithReversed direction = new DirectionWithReversed(tile.tile().findDirection(border), false);
        allBorders.put(border, direction);

        // find valid position (and rotation or flip) for main tile and each neighbour already rotated
        return new TileWithPosition(neighbour.rotateToAndFlip(allBorders), direction.direction().position(tile.position()));
    }

    private Tile findTile(int tileId) {
        return tiles().parallelStream()
                .filter(tile -> tile.id() == tileId)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find tile with id " + tileId));
    }

    private void printImage(Collection<TileWithPosition> tiles) {
        System.out.println("Current image...");
        int yMin = findMin(tiles, Position::y);
        int xMin = findMin(tiles, Position::x);
        int yMax = findMax(tiles, Position::y);
        int xMax = findMax(tiles, Position::x);
        for (int yTemp = yMin; yTemp <= yMax; yTemp++) {
            for (int xTemp = xMin; xTemp <= xMax; xTemp++) {
                int y = yTemp;
                int x = xTemp;
                tiles.stream()
                        .filter(tile -> tile.position().y() == y && tile.position().x() == x)
                        .findAny()
                        .map(TileWithPosition::tile)
                        .map(Tile::id)
                        .ifPresentOrElse(System.out::print, () -> System.out.print("    "));
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private Integer findMin(Collection<TileWithPosition> tiles, Function<Position, Integer> function) {
        return tiles.parallelStream()
                .map(TileWithPosition::position)
                .map(function)
                .min(Integer::compareTo)
                .orElse(0);
    }

    private Integer findMax(Collection<TileWithPosition> tiles, Function<Position, Integer> function) {
        return tiles.parallelStream()
                .map(TileWithPosition::position)
                .map(function)
                .max(Integer::compareTo)
                .orElse(0);
    }

    @Override
    public String toString() {
        return new ImageConverter(sortedTiles).convertToTile(true).toString();
    }
}