package com.mzielinski.advent.of.code.day20;

import com.mzielinski.advent.of.code.utils.MultilineReadFile;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class TilesReader implements MultilineReadFile<Tile> {

    private final Pattern numberPattern = Pattern.compile("[\\d]+");

    @Override
    public Tile convertLine(String input, int lineNumber) {
        List<String> lines = Arrays.stream(input.split(delimiter())).collect(toList());

        int id = lines.stream()
                .map(numberPattern::matcher)
                .filter(Matcher::find)
                .map(Matcher::group)
                .map(Integer::parseInt)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("cannot file any tile number in string " + input));

        List<String> tileRows = lines.stream()
                .filter(line -> !line.startsWith("Tile"))
                .collect(toList());

        List<Point> tile = IntStream.range(0, tileRows.size())
                .mapToObj(y -> {
                    char[] row = tileRows.get(y).toCharArray();
                    return IntStream.range(0, row.length)
                            .mapToObj(x -> new Point(y, x, row[x]));
                })
                .flatMap(Function.identity())
                .collect(toList());
        return new Tile(id, tile);
    }
}
