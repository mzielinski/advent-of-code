package com.mzielinski.advent.of.code.day22

import spock.lang.Specification
import spock.lang.Unroll

class Day22Test extends Specification {

    @Unroll
    def 'should find winning deck for #filePath'() {
        expect:
        new Day22().printWinningDeck(filePath) == result

        where:
        filePath       || result
        'day22/01.txt' || '3, 2, 10, 6, 8, 5, 9, 4, 7, 1'
    }

    @Unroll
    def 'should calculate winner score for $filePath'() {
        expect:
        new Day22().winnerScore(filePath) == result

        where:
        filePath       || result
        'day22/01.txt' || 306
        'day22/02.txt' || 33473
    }
}
