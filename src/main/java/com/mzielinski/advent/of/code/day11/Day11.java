package com.mzielinski.advent.of.code.day11;

import com.mzielinski.advent.of.code.day11.Board.Piece;

import java.util.function.Predicate;

import static com.mzielinski.advent.of.code.day11.Board.Piece.*;

public class Day11 {

    public Board findStableBoard(Board board) {
        Board nextBoard = performSingleRound(board);
        if (!nextBoard.equals(board)) {
            return findStableBoard(nextBoard);
        }
        return nextBoard;
    }

    public Board performSingleRound(Board board) {
        Piece[][] pieces = board.covertToArray();
        for (int y = 0; y < pieces.length; y++) {
            for (int x = 0; x < pieces[y].length; x++) {
                pieces[y][x] = switch (pieces[y][x]) {
                    case EMPTY_SEAT -> checkNeighbours(board, y, x, piece -> piece == OCCUPIED_SEAT) == 0
                            ? OCCUPIED_SEAT
                            : EMPTY_SEAT;
                    case OCCUPIED_SEAT -> checkNeighbours(board, y, x, piece -> piece == OCCUPIED_SEAT) > 4
                            ? EMPTY_SEAT
                            : OCCUPIED_SEAT;
                    case FLOOR -> FLOOR;
                };
            }
        }
        return new Board(pieces);
    }

    private int checkNeighbours(Board board, int y, int x, Predicate<Piece> predicate) {
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
