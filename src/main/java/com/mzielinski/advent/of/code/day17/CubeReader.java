package com.mzielinski.advent.of.code.day17;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.ArrayList;
import java.util.List;

public class CubeReader implements ReadFile<List<Point3D>> {

    public static CubeReader cubeReader() {
        return new CubeReader();
    }

    private int yIndex = 0;

    @Override
    public List<Point3D> getRecordFromLine(String nextLine) {
        List<Point3D> activePoints = new ArrayList<>();
        int xIndex = 0;
        for (char c : nextLine.toCharArray()) {
            if (c == '#') {
                activePoints.add(new Point3D(0, yIndex, xIndex));
            }
            xIndex++;
        }
        yIndex++;
        return activePoints;
    }
}
