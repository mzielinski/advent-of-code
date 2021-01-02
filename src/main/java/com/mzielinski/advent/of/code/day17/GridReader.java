package com.mzielinski.advent.of.code.day17;

import com.mzielinski.advent.of.code.day17.geometry.Point;
import com.mzielinski.advent.of.code.day17.geometry.PointFactory;
import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.ArrayList;
import java.util.List;

public record GridReader<T extends Point<T>>(PointFactory<T> pointFactory) implements ReadFile<List<T>> {

    @Override
    public List<T> convertLine(String nextLine, int lineNumber) {
        List<T> activePoints = new ArrayList<>();
        int xIndex = 0;
        for (char c : nextLine.toCharArray()) {
            if (c == '#') {
                activePoints.add(pointFactory.createPoint(0, lineNumber, xIndex, 0));
            }
            xIndex++;
        }
        return activePoints;
    }
}
