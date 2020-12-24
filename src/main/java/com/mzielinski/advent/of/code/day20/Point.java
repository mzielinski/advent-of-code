package com.mzielinski.advent.of.code.day20;

record Point(int x, int y, char value) {

    @Override
    public String toString() {
        return Character.toString(value());
    }
}
