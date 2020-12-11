package com.mzielinski.advent.of.code.day11;

public enum MoveStrategy {

    LEFT(0, -1),
    RIGHT(0, 1),
    UP(-1, 0),
    DOWN(1, 0),
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1);

    private final int yChanger;
    private final int xChanger;

    MoveStrategy(int yChanger, int xChanger) {
        this.yChanger = yChanger;
        this.xChanger = xChanger;
    }

    public int nextRow(int row) {
        return row + yChanger;
    }

    public int nextColumn(int column) {
        return column + xChanger;
    }

    boolean isEdge(int y, int x, int rowSize, int columnSize) {
        if ((xChanger > 0 && x >= columnSize - 1) || (yChanger > 0 && y >= rowSize - 1)) return true;
        return (xChanger < 0 && x <= 0) || (yChanger < 0 && y <= 0);
    }

    @Override
    public String toString() {
        return name() + " → yChanger=" + yChanger + ", xChanger=" + xChanger;
    }
}

