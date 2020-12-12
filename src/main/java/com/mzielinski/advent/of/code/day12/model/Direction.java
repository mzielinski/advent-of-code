package com.mzielinski.advent.of.code.day12.model;

import com.mzielinski.advent.of.code.day12.model.position.Position;

public enum Direction {
    NORTH {
        @Override
        public Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.moveForward(instruction, this);
            return super.calculatePosition(position, instruction);
        }

        @Override
        public Direction calculateNewDirection(Instruction instruction) {
            return switch (instruction.command()) {
                case R -> switch (instruction.value()) {
                    case 90 -> EAST;
                    case 180 -> SOUTH;
                    case 270 -> WEST;
                    default -> this;
                };
                case L -> switch (instruction.value()) {
                    case 90 -> WEST;
                    case 180 -> SOUTH;
                    case 270 -> EAST;
                    default -> this;
                };
                default -> this;
            };
        }
    }, SOUTH {
        @Override
        public Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.moveForward(instruction, this);
            return super.calculatePosition(position, instruction);
        }

        @Override
        public Direction calculateNewDirection(Instruction instruction) {
            return switch (instruction.command()) {
                case R -> switch (instruction.value()) {
                    case 90 -> WEST;
                    case 180 -> NORTH;
                    case 270 -> EAST;
                    default -> this;
                };
                case L -> switch (instruction.value()) {
                    case 90 -> EAST;
                    case 180 -> NORTH;
                    case 270 -> WEST;
                    default -> this;
                };
                default -> this;
            };
        }
    }, WEST {
        @Override
        public Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.moveForward(instruction, this);
            return super.calculatePosition(position, instruction);
        }

        @Override
        public Direction calculateNewDirection(Instruction instruction) {
            return switch (instruction.command()) {
                case R -> switch (instruction.value()) {
                    case 90 -> NORTH;
                    case 180 -> EAST;
                    case 270 -> SOUTH;
                    default -> this;
                };
                case L -> switch (instruction.value()) {
                    case 90 -> SOUTH;
                    case 180 -> EAST;
                    case 270 -> NORTH;
                    default -> this;
                };
                default -> this;
            };
        }
    }, EAST {
        @Override
        public Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.moveForward(instruction, this);
            return super.calculatePosition(position, instruction);
        }

        @Override
        public Direction calculateNewDirection(Instruction instruction) {
            return switch (instruction.command()) {
                case R -> switch (instruction.value()) {
                    case 90 -> SOUTH;
                    case 180 -> WEST;
                    case 270 -> NORTH;
                    default -> this;
                };
                case L -> switch (instruction.value()) {
                    case 90 -> NORTH;
                    case 180 -> WEST;
                    case 270 -> SOUTH;
                    default -> this;
                };
                default -> this;
            };
        }
    };

    public <T extends Position> Position calculatePosition(T position, Instruction instruction) {
        return switch (instruction.command()) {
            case N -> position.changeNorthPosition(instruction, this);
            case S -> position.changeSouthPosition(instruction, this);
            case E -> position.changeEastPosition(instruction, this);
            case W -> position.changeWestPosition(instruction, this);
            default -> position;
        };
    }

    public abstract Direction calculateNewDirection(Instruction instruction);
}
