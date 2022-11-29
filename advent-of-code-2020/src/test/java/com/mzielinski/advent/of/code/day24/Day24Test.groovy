package com.mzielinski.advent.of.code.day24

import spock.lang.Specification
import spock.lang.Unroll

class Day24Test extends Specification {

    @Unroll
    def 'should flip color from WHITE to BLACK'() {
        expect:
        new Day24().countBlackTiles(input) == result

        where:
        input          || result
        'day24/00.txt' || 2
    }

    @Unroll
    def 'should calculate number of BLACK tiles for input #input'() {
        expect:
        new Day24().countBlackTiles(input) == result

        where:
        input          || result
        'day24/01.txt' || 10
        'day24/02.txt' || 269
    }

    @Unroll
    def 'should calculate number of BLACK tiles after #days day(s) for input #input'() {
        expect:
        new Day24().countBlackTiles(input, days) == result

        where:
        days | input          || result
        1    | 'day24/01.txt' || 15
        2    | 'day24/01.txt' || 12
        5    | 'day24/01.txt' || 23
        50   | 'day24/01.txt' || 566
        60   | 'day24/01.txt' || 788
        100  | 'day24/01.txt' || 2208
        100  | 'day24/02.txt' || 3667 }
}
