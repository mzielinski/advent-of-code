package com.mzielinski.advent.of.code.day17;

import com.mzielinski.advent.of.code.day17.geometry.Point;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record Grid<T>(Set<Point<T>> activePoints) {

    public static <T extends Point<T>> Grid<T> initializeCube(String filePath, CubeReader<T> cubeReader) {
        return new Grid<>(cubeReader
                .readFile(filePath)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()));
    }

    public long calculateActive() {
        return activePoints.size();
    }
}

