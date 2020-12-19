package com.mzielinski.advent.of.code.day17;

import com.mzielinski.advent.of.code.day17.geometry.Point;
import com.mzielinski.advent.of.code.day17.geometry.PointFactory;
import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.ArrayList;
import java.util.List;

public class GridReader<T extends Point<T>> implements ReadFile<List<T>> {

    private final PointFactory<T> pointFactory;
    private int yIndex = 0;

    public GridReader(PointFactory<T> pointFactory) {
        this.pointFactory = pointFactory;
    }

    @Override
    public void cleanup() {
        yIndex = 0;
    }

    @Override
    public List<T> getRecordFromLine(String nextLine) {
        List<T> activePoints = new ArrayList<>();
        int xIndex = 0;
        for (char c : nextLine.toCharArray()) {
            if (c == '#') {
                activePoints.add(pointFactory.createPoint(0, yIndex, xIndex, 0));
            }
            xIndex++;
        }
        yIndex++;
        return activePoints;
    }
}