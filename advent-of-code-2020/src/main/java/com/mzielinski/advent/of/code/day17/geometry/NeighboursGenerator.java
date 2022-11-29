package com.mzielinski.advent.of.code.day17.geometry;

import java.util.List;

public interface NeighboursGenerator<T extends Point<T>> {
    List<T> combinations();
}
