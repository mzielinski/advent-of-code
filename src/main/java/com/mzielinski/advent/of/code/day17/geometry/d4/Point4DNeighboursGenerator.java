package com.mzielinski.advent.of.code.day17.geometry.d4;

import com.mzielinski.advent.of.code.day17.geometry.NeighboursGenerator;
import org.paukov.combinatorics3.Generator;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Point4DNeighboursGenerator implements NeighboursGenerator<Point4D> {

    private final List<Point4D> combinations;

    public Point4DNeighboursGenerator() {
        this.combinations = Generator.permutation(-1, 0, 1)
                .withRepetitions(4)
                .stream()
                .map(point -> new Point4D(point.get(0), point.get(1), point.get(2), point.get(3)))
                .collect(toList());
    }

    @Override
    public List<Point4D> combinations() {
        return new Point4DNeighboursGenerator().combinations;
    }

    @Override
    public String toString() {
        return combinations().toString();
    }
}

