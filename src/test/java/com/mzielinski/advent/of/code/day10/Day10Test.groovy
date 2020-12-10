package com.mzielinski.advent.of.code.day10

import spock.lang.Specification
import spock.lang.Unroll

class Day10Test extends Specification {

    @Unroll
    def 'should calculate the number of 1-jolt differences multiplied by the number of 3-jolt differences'() {
        expect:
        new Day10().findDifferencesForJoltage(path) == result

        where:
        path           || result
        'day10/00.txt' || 35
        'day10/01.txt' || 220
        'day10/02.txt' || 2414
    }
}
