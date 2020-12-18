package com.mzielinski.advent.of.code.day17;

import java.util.Set;

public record Point3D(int z, int y, int x) {

    record Point3DWithNeighbours(Point3D point, Set<Point3D> neighbours) {
    }

    public Point3D next(Point3D changer) {
        return new Point3D(z + changer.z(), y + changer.y(), x + changer.x());
    }
}
