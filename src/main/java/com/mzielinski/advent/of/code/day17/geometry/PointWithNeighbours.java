package com.mzielinski.advent.of.code.day17.geometry;

import java.util.Set;

public record PointWithNeighbours<T extends Point<T>>(Point<T> point, Set<Point<T>> neighbours) {

    public Point<T> getPoint() {
        return point;
    }

    public Set<Point<T>> getNeighbours() {
        return neighbours;
    }
}