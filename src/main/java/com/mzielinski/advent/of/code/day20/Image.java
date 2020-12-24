package com.mzielinski.advent.of.code.day20;

import java.util.List;

record Image(List<List<Tile>> tiles) {

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        for (List<Tile> row : tiles) {
            for (Tile column : row) {
                sb.append(column);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
