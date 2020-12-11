package com.mzielinski.advent.of.code.day11

import spock.lang.Specification
import spock.lang.Unroll

class Day11Test extends Specification {

    @Unroll
    def 'should verify that board #initialBoard is changed to #expectedBoard for single round'() {
        given:
        def board = Board.convertTo(initialBoard)

        expect:
        new Day11().performSingleRound(board) == Board.convertTo(expectedBoard)

        where:
        initialBoard            || expectedBoard
        'day11/01-stage-01.txt' || 'day11/01-stage-02.txt'
        'day11/01-stage-02.txt' || 'day11/01-stage-03.txt'
        'day11/01-stage-03.txt' || 'day11/01-stage-04.txt'
        'day11/01-stage-04.txt' || 'day11/01-stage-05.txt'
        'day11/01-stage-05.txt' || 'day11/01-stage-06.txt'
    }

    def 'should calculate how many seats are occupied after stabilization'() {
        given:
        Board stableBoard = new Day11().findStableBoard(Board.convertTo(initialBoard))

        expect:
        stableBoard.calculateNumberOfOccupiedSeats() == expectedResult

        where:
        initialBoard            || expectedResult
        'day11/01-stage-01.txt' || 37
        'day11/02.txt'          || 2319
    }
}
