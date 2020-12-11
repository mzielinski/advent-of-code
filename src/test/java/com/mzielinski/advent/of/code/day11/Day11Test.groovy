package com.mzielinski.advent.of.code.day11

import spock.lang.Specification
import spock.lang.Unroll

import static com.mzielinski.advent.of.code.day11.Board.Piece.FLOOR

class Day11Test extends Specification {

    @Unroll
    def 'should verify that board #initialBoard is changed to #expectedBoard for single round'() {
        given:
        def board = Board.convertTo(initialBoard)

        expect:
        new Day11Part1().performSingleRound(board) == Board.convertTo(expectedBoard)

        where:
        initialBoard            || expectedBoard
        'day11/01-stage-01.txt' || 'day11/01-stage-02.txt'
        'day11/01-stage-02.txt' || 'day11/01-stage-03.txt'
        'day11/01-stage-03.txt' || 'day11/01-stage-04.txt'
        'day11/01-stage-04.txt' || 'day11/01-stage-05.txt'
        'day11/01-stage-05.txt' || 'day11/01-stage-06.txt'
    }

    def 'should calculate how many seats are occupied after stabilization in part 1'() {
        given:
        Board stableBoard = new Day11Part1().findStableBoard(Board.convertTo(initialBoard))

        expect:
        stableBoard.calculateNumberOfOccupiedSeats() == expectedResult

        where:
        initialBoard            || expectedResult
        'day11/01-stage-01.txt' || 37
        'day11/02.txt'          || 2319
    }

    @Unroll
    def 'should find #result neighbors for board #initialBoard'() {
        given:
        def objectUnderTest = new Day11Part2()
        def board = Board.convertTo(initialBoard)

        expect:
        objectUnderTest.countNeighbours(board, row, column, { it != FLOOR }) == result

        where:
        initialBoard                            | row | column || result
        'day11/03-find-neighbours-part2-01.txt' | 4   | 3      || 8
        'day11/03-find-neighbours-part2-02.txt' | 1   | 1      || 0
        'day11/03-find-neighbours-part2-03.txt' | 3   | 3      || 0
        'day11/03-find-neighbours-part2-04.txt' | 0   | 0      || 3
    }

    @Unroll
    def 'should calculate how many seats are occupied after stabilization in part 2'() {
        given:
        Board stableBoard = new Day11Part2().findStableBoard(Board.convertTo(initialBoard))

        expect:
        stableBoard.calculateNumberOfOccupiedSeats() == expectedResult

        where:
        initialBoard            || expectedResult
        'day11/01-stage-01.txt' || 26
        'day11/02.txt'          || 2117
    }

    @Unroll
    def 'should verify board modification from #stage to #nextStage for single round in part 2'() {
        given:
        def board = Board.convertTo("day11/04-stage-0${stage}.txt")

        expect:
        new Day11Part2().performSingleRound(board) == Board.convertTo("day11/04-stage-0${stage + 1}.txt")

        where:
        stage << (1..6)
        nextStage = stage + 1
    }
}
