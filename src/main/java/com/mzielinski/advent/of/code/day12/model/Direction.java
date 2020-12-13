package com.mzielinski.advent.of.code.day12.model;

import com.mzielinski.advent.of.code.day12.model.position.Position;

public enum Direction {
    NORTH {
        @Override
        public Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.moveForward(instruction, this);
            return super.calculatePosition(position, instruction);
        }
    }, SOUTH {
        @Override
        public Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.moveForward(instruction, this);
            return super.calculatePosition(position, instruction);
        }

    }, WEST {
        @Override
        public Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.moveForward(instruction, this);
            return super.calculatePosition(position, instruction);
        }
    }, EAST {
        @Override
        public Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.moveForward(instruction, this);
            return super.calculatePosition(position, instruction);
        }
    };

    public <T extends Position> Position calculatePosition(T position, Instruction instruction) {
        return switch (instruction.command()) {
            case N -> position.changeNorthPosition(instruction);
            case S -> position.changeSouthPosition(instruction);
            case E -> position.changeEastPosition(instruction);
            case W -> position.changeWestPosition(instruction);
            default -> position;
        };
    }

    public Direction calculateNewDirection(Instruction instruction, Direction current, Direction... directions) {
        return switch (instruction.command()) {
            case R -> switch (instruction.value()) {
                case 90 -> directions[0];
                case 180 -> directions[1];
                case 270 -> directions[2];
                default -> current;
            };
            case L -> switch (instruction.value()) {
                case 90 -> directions[2];
                case 180 -> directions[1];
                case 270 -> directions[0];
                default -> current;
            };
            default -> current;
        };
    }
}
