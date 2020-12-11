package com.mzielinski.advent.of.code.day11;

import com.mzielinski.advent.of.code.day11.Board.Piece;

import java.util.Arrays;
import java.util.function.Predicate;

import static com.mzielinski.advent.of.code.day11.Board.Piece.*;

class Day11Part1 extends Day11 {

    public Day11Part1() {
        super(5);
    }

    @Override
    boolean firstBlockingPiece(Piece piece) {
        return piece == OCCUPIED_SEAT;
    }

    @Override
    int countNeighbours(Board board, int y, int x, Predicate<Piece> predicate) {
        int startPosX = (x - 1 < 0) ? x : x - 1;
        int startPosY = (y - 1 < 0) ? y : y - 1;
        int endPosX = (x + 1 >= board.columnSize()) ? x : x + 1;
        int endPosY = (y + 1 >= board.rowSize()) ? y : y + 1;
        int counter = 0;
        for (int rowNum = startPosY; rowNum <= endPosY; rowNum++) {
            for (int colNum = startPosX; colNum <= endPosX; colNum++) {
                if (predicate.test(board.findPiece(rowNum, colNum))) {
                    counter++;
                }
            }
        }
        return counter;
    }
}

class Day11Part2 extends Day11 {

    public Day11Part2() {
        super(5);
    }

    @Override
    boolean firstBlockingPiece(Piece piece) {
        return piece == OCCUPIED_SEAT || piece == EMPTY_SEAT;
    }

    @Override
    int countNeighbours(Board board, int y, int x, Predicate<Piece> predicate) {
        return Arrays.stream(MoveStrategy.values()).reduce(0,
                (partialResult, moveStrategy) ->
                        partialResult + (checkNeighbours(y, x, board, predicate, moveStrategy) == OCCUPIED_SEAT ? 1 : 0),
                Integer::sum);
    }

    private Piece checkNeighbours(int y, int x, Board board, Predicate<Piece> predicate, MoveStrategy move) {
        int nextRow = move.nextRow(y);
        int nextColumn = move.nextColumn(x);
        if (!board.validCoordinates(nextRow, nextColumn)) return NIL;
        if (predicate.test(board.findPiece(nextRow, nextColumn))) {
            return board.findPiece(nextRow, nextColumn);
        }
        if (move.isEdge(nextRow, nextColumn, board.rowSize(), board.columnSize())) return NIL;
        return checkNeighbours(nextRow, nextColumn, board, predicate, move);
    }
}

abstract class Day11 {

    private final int maxOccupiedSeats;

    public Day11(int maxOccupiedSeats) {
        this.maxOccupiedSeats = maxOccupiedSeats;
    }

    public Board findStableBoard(Board board) {
        Board nextBoard = performSingleRound(board);
        if (!nextBoard.equals(board)) {
            return findStableBoard(nextBoard);
        }
        return nextBoard;
    }

    public Board performSingleRound(Board board) {
        Piece[][] pieces = board.toArray();
        for (int y = 0; y < pieces.length; y++) {
            for (int x = 0; x < pieces[y].length; x++) {
                pieces[y][x] = switch (pieces[y][x]) {
                    case EMPTY_SEAT -> countNeighbours(board, y, x, this::firstBlockingPiece) == 0
                            ? OCCUPIED_SEAT
                            : EMPTY_SEAT;
                    case OCCUPIED_SEAT -> countNeighbours(board, y, x, this::firstBlockingPiece) >= maxOccupiedSeats
                            ? EMPTY_SEAT
                            : OCCUPIED_SEAT;
                    case FLOOR -> FLOOR;
                    case NIL -> throw new IllegalStateException("Only internal piece. Should not be set in board");
                };
            }
        }
        return new Board(pieces);
    }

    abstract boolean firstBlockingPiece(Piece piece);

    abstract int countNeighbours(Board board, int y, int x, Predicate<Piece> predicate);
}
