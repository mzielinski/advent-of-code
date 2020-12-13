package com.mzielinski.advent.of.code.day12;

import com.mzielinski.advent.of.code.day12.input.InstructionsReader;
import com.mzielinski.advent.of.code.day12.model.Ferry;
import com.mzielinski.advent.of.code.day12.model.Instruction;
import com.mzielinski.advent.of.code.day12.model.position.Position;
import com.mzielinski.advent.of.code.day12.model.position.StandardPositionChanger;
import com.mzielinski.advent.of.code.day12.model.position.WayPointPositionChanger;

import java.util.stream.Stream;

public record Day12(Ferry<Position> ferryMap) {

    public static Day12 part1() {
        return new Day12(new Ferry<>(StandardPositionChanger.INITIAL));
    }

    public static Day12 part2() {
        return new Day12(new Ferry<>(WayPointPositionChanger.INITIAL));
    }

    public Ferry<? extends Position> calculatePositionsOnMap(String filePath) {
        try (InstructionsReader instructions = new InstructionsReader(filePath)) {
            Stream<Instruction> stream = instructions.getStream();
            return stream.reduce(ferryMap, Ferry::applyInstruction, ($1, $2) -> null);
        }
    }
}
