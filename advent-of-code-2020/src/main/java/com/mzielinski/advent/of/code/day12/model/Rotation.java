package com.mzielinski.advent.of.code.day12.model;

import java.util.List;

import static com.mzielinski.advent.of.code.day12.model.Direction.*;

public enum Rotation {
    R90, R180, R270, L90, L180, L270;

    public static List<Rotation> findRotation(Direction first, Direction second) {
        if (findRotations(first, second, EAST, SOUTH, WEST, NORTH)) {
            return List.of(R90, L270);
        } else if (findRotations(first, second, SOUTH, WEST, NORTH, EAST)) {
            return List.of(R180, L180);
        } else if (findRotations(first, second, WEST, NORTH, EAST, SOUTH)) {
            return List.of(R270, L90);
        }
        return List.of();
    }

    private static boolean findRotations(Direction first, Direction second, Direction... directions) {
        return (first == NORTH && second == directions[0]) ||
                (first == EAST && second == directions[1]) ||
                (first == SOUTH && second == directions[2]) ||
                (first == WEST && second == directions[3]);
    }
}