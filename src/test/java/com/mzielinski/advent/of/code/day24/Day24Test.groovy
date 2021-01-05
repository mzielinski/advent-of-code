package com.mzielinski.advent.of.code.day24

import spock.lang.Specification
import spock.lang.Unroll

class Day24Test extends Specification {

    @Unroll
    def 'should flip color from WHITE to BLACK'() {
        expect:
        new Day24().checkColor(input)[index] == color

        where:
        index | input          || color
        0     | 'day24/00.txt' || 'BLACK'
        1     | 'day24/00.txt' || 'BLACK'
    }

    @Unroll
    def 'should calculate number of BLACK tiles for input #input'() {
        expect:
        new Day24().checkColor(input).findAll {it == 'BLACK'}.size() == result

        where:
        input          || result
        'day24/01.txt' || 10
        'day24/02.txt' || 269
    }
}
