package com.mzielinski.advent.of.code.day20;

import static com.mzielinski.advent.of.code.day20.Tile.MONSTER_CORDS;

public class Day20 {

    // only narrow corners will have only 2 boarders common
    public static final int NUMBER_OF_NARROW_COMMON_BOARDERS = 2;

    public static long multiplyCornerIds(String filePath) {
        Image image = new Image(new TilesReader().readFile(filePath));
        return image.tiles().stream()
                .filter(tile -> tile.countCommonBoards(image) == NUMBER_OF_NARROW_COMMON_BOARDERS)
                .map(Tile::id)
                .reduce(1L, (partialResult, id) -> partialResult * id, ($1, $2) -> null);
    }

    public static int countActivePointsExceptMonsters(String filePath) {
        Tile tile = convertToImageWithoutBorders(filePath);
        int numberOfMonsters = tile.countMonsters();
        return tile.numberOfActivePoints() - numberOfMonsters * MONSTER_CORDS.size();
    }

    public static Tile convertToImageWithoutBorders(String filePath) {
        Image image = new Image(new TilesReader().readFile(filePath)).createImage();
        return new ImageConverter(image.sortedTiles()).convertToTile(false);
    }

    public static Image buildImage(String filePath) {
        Image image = new Image(new TilesReader().readFile(filePath));
        return image.createImage();
    }
}