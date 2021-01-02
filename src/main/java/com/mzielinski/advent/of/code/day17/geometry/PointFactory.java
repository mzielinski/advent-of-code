package com.mzielinski.advent.of.code.day17.geometry;

@FunctionalInterface
public interface PointFactory<T extends Point<T>> {

    /**
     * Create the element for the supplied player.
     *
     * @return the element object
     */
    T createPoint(int z, int y, int x, int w);
}