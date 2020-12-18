package com.mzielinski.advent.of.code.day17;

import org.paukov.combinatorics3.Generator;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class GridMoveStrategy {

    private final List<Point3D> combinations;

    private GridMoveStrategy() {
        this.combinations = Generator.permutation(-1, 0, 1)
                .withRepetitions(3)
                .stream()
                .map(point -> new Point3D(point.get(0), point.get(1), point.get(2)))
                .collect(toList());
    }

    public static List<Point3D> combinations() {
        return new GridMoveStrategy().combinations;
    }

    @Override
    public String toString() {
        return combinations().toString();
    }
}

