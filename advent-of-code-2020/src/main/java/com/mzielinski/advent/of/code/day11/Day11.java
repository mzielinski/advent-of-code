package com.mzielinski.advent.of.code.day11;

import com.mzielinski.advent.of.code.day11.Board.Piece;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import static com.mzielinski.advent.of.code.day11.Board.Piece.*;

class Day11Part1 extends Day11 {

    public Day11Part1() {
        super(4);
    }

    @Override
    boolean firstBlockingPiece(Piece piece) {
        return piece == OCCUPIED_SEAT;
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
    Optional<Piece> checkNeighbours(int y, int x, Board board, Predicate<Piece> predicate, MoveStrategy move) {
        if (move.isEdge(y, x, board.rowSize(), board.columnSize())) {
            return Optional.empty();
        }

        Optional<Piece> piece = super.checkNeighbours(y, x, board, predicate, move);
        if (piece.isPresent()) {
            return piece;
        }
        return checkNeighbours(move.nextRow(y), move.nextColumn(x), board, predicate, move);
    }
}

abstract class Day11 {

    private final int maxOccupiedSeats;

    public Day11(int maxOccupiedSeats) {
        this.maxOccupiedSeats = maxOccupiedSeats;
    }

    abstract boolean firstBlockingPiece(Piece piece);

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
                };
            }
        }
        return new Board(pieces);
    }

    Optional<Piece> checkNeighbours(int y, int x, Board board, Predicate<Piece> predicate, MoveStrategy move) {
        int nextRow = move.nextRow(y);
        int nextColumn = move.nextColumn(x);
        if (!board.outOfBoard(nextRow, nextColumn)) return Optional.empty();

        final Piece piece = board.findPiece(nextRow, nextColumn);
        return Optional.of(piece).filter(predicate);
    }

    int countNeighbours(Board board, int y, int x, Predicate<Piece> predicate) {
        return Arrays.stream(MoveStrategy.values()).reduce(0,
                (partialResult, moveStrategy) ->
                        checkNeighbours(y, x, board, predicate, moveStrategy)
                                .filter(piece -> piece == OCCUPIED_SEAT)
                                .map(a -> partialResult + 1)
                                .orElse(partialResult), Integer::sum);
    }
}
