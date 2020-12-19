package com.mzielinski.advent.of.code.day17.geometry.d3;

import com.mzielinski.advent.of.code.day17.geometry.NeighboursGenerator;
import org.paukov.combinatorics3.Generator;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Point3DNeighboursGenerator implements NeighboursGenerator<Point3D> {

    private final List<Point3D> combinations;

    public Point3DNeighboursGenerator() {
        this.combinations = Generator.permutation(-1, 0, 1)
                .withRepetitions(3)
                .stream()
                .map(point -> new Point3D(point.get(0), point.get(1), point.get(2)))
                .collect(toList());
    }

    @Override
    public List<Point3D> combinations() {
        return new Point3DNeighboursGenerator().combinations;
    }

    @Override
    public String toString() {
        return combinations().toString();
    }
}

