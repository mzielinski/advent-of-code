package com.mzielinski.advent.of.code.day20;

record Position(int y, int x) {
}

record Point(int y, int x, char value) {

    @Override
    public String toString() {
        return Character.toString(value());
    }

    public boolean isActive() {
        return value == '#';
    }
}
