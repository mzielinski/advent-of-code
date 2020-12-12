package com.mzielinski.advent.of.code.day11;

import java.util.*;

import static com.mzielinski.advent.of.code.day11.Board.Piece.OCCUPIED_SEAT;
import static java.util.stream.Collectors.toList;

public class Board {

    public enum Piece {

        FLOOR('.'), EMPTY_SEAT('L'), OCCUPIED_SEAT('#');

        private final char value;

        Piece(char value) {
            this.value = value;
        }

        public static Piece valueOf(char value) {
            return Arrays.stream(Piece.values())
                    .filter(piece -> piece.value == value)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Value " + value + " is not supported as a Piece"));
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    private final List<List<Piece>> board;

    public Board(List<List<Piece>> board) {
        this.board = new ArrayList<>(board);
    }

    public Board(Piece[][] pieces) {
        this(Arrays.stream(pieces)
                .map(a -> new ArrayList<>(Arrays.stream(a).collect(toList())))
                .collect(toList()));
    }

    public int columnSize() {
        return Optional.of(board)
                .filter(board -> board.size() > 0)
                .map(board -> board.get(0))
                .map(List::size)
                .orElseThrow(() -> new IllegalStateException("Board cannot be empty"));
    }

    public boolean outOfBoard(int y, int x) {
        return y >= 0 && x >= 0 && y < rowSize() && x < columnSize();
    }

    public int rowSize() {
        return Optional.of(board)
                .map(List::size)
                .orElseThrow(() -> new IllegalStateException("Board cannot be empty"));
    }

    public Piece findPiece(int row, int column) {
        return Optional.of(board)
                .map(rows -> rows.get(row))
                .map(columns -> columns.get(column))
                .orElseThrow(() -> new IllegalStateException("Piece in row " + row + " and column " + column + " cannot be found"));
    }

    public long calculateNumberOfOccupiedSeats() {
        return board.stream()
                .flatMap(Collection::stream)
                .filter(piece -> piece == OCCUPIED_SEAT).count();
    }

    public Piece[][] toArray() {
        return board.stream()
                .map(arr -> arr.toArray(Piece[]::new))
                .toArray(Piece[][]::new);
    }

    public static Board instanceOf(String path) {
        List<List<Piece>> lists = new BoardReader().readFile(path);
        return new Board(lists);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return Objects.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        for (List<Piece> pieces : board) {
            for (Piece piece : pieces) {
                sb.append(piece);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

