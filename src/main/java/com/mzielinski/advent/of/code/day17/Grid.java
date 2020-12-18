package com.mzielinski.advent.of.code.day17;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record Grid(Set<Point3D> activePoints) {

    public static Grid initializeCube(String filePath) {
        return new Grid(new CubeReader()
                .readFile(filePath)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()));
    }

    public long calculateActive() {
        return activePoints.size();
    }
}

