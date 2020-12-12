package com.mzielinski.advent.of.code.day12.model;

public enum Direction {
    NORTH {
        @Override
        Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.changeNorthPosition(instruction.value());
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
        Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.changeSouthPosition(instruction.value());
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
        Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.changeWestPosition(instruction.value());
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
        Position calculatePosition(Position position, Instruction instruction) {
            if (instruction.command() == Command.F) return position.changeEastPosition(instruction.value());
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

    Position calculatePosition(Position position, Instruction instruction) {
        return switch (instruction.command()) {
            case N -> position.changeNorthPosition(instruction.value());
            case S -> position.changeSouthPosition(instruction.value());
            case E -> position.changeEastPosition(instruction.value());
            case W -> position.changeWestPosition(instruction.value());
            default -> position;
        };
    }

    public abstract Direction calculateNewDirection(Instruction instruction);
}
