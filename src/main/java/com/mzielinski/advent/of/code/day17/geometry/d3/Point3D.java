package com.mzielinski.advent.of.code.day17.geometry.d3;

import com.mzielinski.advent.of.code.day17.geometry.Point;

import java.util.Set;

public record Point3D(int z, int y, int x) implements Point<Point3D> {

    public record Point3DWithNeighbours(Point3D point, Set<Point3D> neighbours) {
    }

    @Override
    public Point3D next(Point3D changer) {
        return new Point3D(z + changer.z(), y + changer.y(), x + changer.x());
    }
}
