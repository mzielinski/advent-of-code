package com.mzielinski.advent.of.code.day12.model.position;

import com.mzielinski.advent.of.code.day12.model.Direction;
import com.mzielinski.advent.of.code.day12.model.Instruction;

public interface Position {

    int calculateManhattanDistance();

    Direction direction();

    Position moveForward(Instruction instruction, Direction direction);

    Position changeNorthPosition(Instruction instruction);

    Position changeSouthPosition(Instruction instruction);

    Position changeEastPosition(Instruction instruction);

    Position changeWestPosition(Instruction instruction);

    Position recalculateDirection(Direction nextDirection, Instruction instruction);

    default Position recalculatePosition(Instruction instruction) {
        return direction().calculatePosition(this, instruction);
    }
}
