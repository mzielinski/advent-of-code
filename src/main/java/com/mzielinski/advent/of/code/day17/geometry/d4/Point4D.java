package com.mzielinski.advent.of.code.day17.geometry.d4;

import com.mzielinski.advent.of.code.day17.geometry.Point;

public record Point4D(int z, int y, int x, int w) implements Point<Point4D> {

    @Override
    public Point4D next(Point4D changer) {
        return new Point4D(z + changer.z(), y + changer.y(), x + changer.x(), w + changer.w());
    }
}
