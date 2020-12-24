package com.mzielinski.advent.of.code.day11;

import com.mzielinski.advent.of.code.day11.Board.Piece;
import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.ArrayList;
import java.util.List;

public class BoardReader implements ReadFile<List<Piece>> {

    @Override
    public List<Piece> getRecordMultiLines(String nextLine, int lineNumber) {
        List<Piece> board = new ArrayList<>();
        for (char c : nextLine.toCharArray()) {
            board.add(Piece.valueOf(c));
        }
        return board;
    }
}
