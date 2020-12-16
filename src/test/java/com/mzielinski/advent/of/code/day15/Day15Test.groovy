package com.mzielinski.advent.of.code.day15

import spock.lang.Specification
import spock.lang.Unroll

class Day15Test extends Specification {

    @Unroll
    def 'should find #result for #number\'th spoken number for given sequence #sequence'() {
        expect:
        new Day15().memoryGame(number, sequence) == result

        where:
        number | sequence             || result
        2020   | [0, 3, 6]            || 436
        2020   | [1, 3, 2]            || 1
        2020   | [2, 1, 3]            || 10
        2020   | [1, 2, 3]            || 27
        2020   | [2, 3, 1]            || 78
        2020   | [3, 2, 1]            || 438
        2020   | [3, 1, 2]            || 1836
        2020   | [8, 0, 17, 4, 1, 12] || 981
    }
}
