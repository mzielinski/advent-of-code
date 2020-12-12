package com.mzielinski.advent.of.code.day12.model.position;

import com.mzielinski.advent.of.code.day12.model.Direction;
import com.mzielinski.advent.of.code.day12.model.Instruction;

public interface Position {

    Position moveForward(Instruction instruction, Direction direction);

    Position changeNorthPosition(Instruction instruction, Direction direction);

    Position changeSouthPosition(Instruction instruction, Direction direction);

    Position changeEastPosition(Instruction instruction, Direction direction);

    Position changeWestPosition(Instruction instruction, Direction direction);

    int calculateManhattanDistance();
}
