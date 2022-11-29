package com.mzielinski.advent.of.code.day17.geometry;

import java.util.Set;

public record PointWithNeighbours<T extends Point<T>>(Point<T> point, Set<Point<T>> neighbours) {
}