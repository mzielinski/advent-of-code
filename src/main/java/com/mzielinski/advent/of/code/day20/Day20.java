package com.mzielinski.advent.of.code.day20;

import java.util.List;

public class Day20 {

    // only narrow corners will have 4 boarders common (first and second neighbour, and tile itself)
    public static final int NUMBER_OF_NARROW_COMMON_BOARDERS = 2 * 2;

    private long multiplyCornerIds(List<Tile> tiles) {
        return tiles.stream().filter(tile -> tile.countCommonBoards(tiles) == NUMBER_OF_NARROW_COMMON_BOARDERS)
                .map(Tile::id)
                .reduce(1L, (partialResult, id) -> partialResult * id, ($1, $2) -> null);
    }

    public static long multiplyCornerIds(String filePath) {
        List<Tile> tiles = new TilesReader().readFile(filePath);
        return new Day20().multiplyCornerIds(tiles);
    }
}