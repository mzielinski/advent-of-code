package com.mzielinski.advent.of.code.day20;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public record ImageConverter(List<TileWithPosition> sortedTiles) {

    public Tile convertToTile(boolean withBoarders) {
        List<Point> points = new ArrayList<>();

        int imageSize = findImageLength();
        int tileSize = findTileLength();

        int yImage = findYPositionOnImage();
        int xImage = findXPositionOnImage();

        AtomicInteger y = new AtomicInteger();
        AtomicInteger x = new AtomicInteger();

        while (yImage != imageSize) {
            int yTemp = yImage;
            int xTemp = xImage;
            AtomicBoolean validLine = new AtomicBoolean(withBoarders);
            sortedTiles.stream()
                    .filter(tile -> tile.position().y() == yTemp)
                    .map(TileWithPosition::tile)
                    .map(Tile::points)
                    .forEachOrdered(tile -> tile.stream()
                            .filter(point -> point.y() == xTemp)
                            .filter(point -> validPoint(withBoarders, point, tileSize))
                            .forEachOrdered(point -> {
                                points.add(new Point(y.get(), x.getAndIncrement(), point.value()));
                                validLine.compareAndSet(false, true);
                            }));
            if (xImage == tileSize) {
                // next image row
                xImage = 0;
                yImage++;
            } else {
                // next image column
                xImage++;
            }
            if (validLine.get()) {
                x.set(0);
                y.getAndIncrement();
            }
        }
        return new Tile(0, points);
    }

    private boolean validPoint(boolean withBoarders, Point point, int tileSize) {
        return withBoarders || !isTileBorder(point, tileSize);
    }

    private int findTileLength() {
        return Optional.of(sortedTiles)
                .map(tiles -> tiles.get(0))
                .map(TileWithPosition::tile)
                .map(Tile::xMax)
                .orElseThrow(() -> new IllegalArgumentException("Cannot file max tile length"));
    }

    private int findImageLength() {
        return Optional.of(sortedTiles)
                .map(tiles -> tiles.get(0))
                .map(TileWithPosition::tile)
                .map(Tile::yMax)
                .orElseThrow(() -> new IllegalArgumentException("Cannot file max image length"));
    }

    private Integer findXPositionOnImage() {
        return Optional.of(sortedTiles)
                .map(tiles -> tiles.get(0))
                .map(TileWithPosition::tile)
                .map(Tile::points)
                .map(points -> points.get(0))
                .map(Point::y)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find column size"));
    }

    private Integer findYPositionOnImage() {
        return Optional.of(sortedTiles)
                .map(tiles -> tiles.get(0))
                .map(TileWithPosition::y)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find row size"));
    }

    private boolean isTileBorder(Point point, int size) {
        return Tile.boardersConditions.entrySet().parallelStream()
                .anyMatch(entry -> entry.getValue().test(point, size));
    }
}
