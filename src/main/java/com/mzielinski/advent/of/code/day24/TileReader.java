package com.mzielinski.advent.of.code.day24;

import com.mzielinski.advent.of.code.day24.Day24.Color;
import com.mzielinski.advent.of.code.day24.Day24.Direction;
import com.mzielinski.advent.of.code.day24.Day24.Position;
import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TileReader implements ReadFile<Day24.Tile> {

    private final Pattern pattern = Pattern.compile("(e|w|se|ne|sw|nw)");

    @Override
    public Day24.Tile convertLine(String nextLine, int lineNumber) {
        Matcher matcher = pattern.matcher(nextLine);
        List<Direction> directions = new ArrayList<>();
        while (matcher.find()) {
            directions.add(Direction.parseTo(matcher.group()));
        }
        return new Day24.Tile(lineNumber, new Position(0, 0), Color.WHITE, directions);
    }
}
