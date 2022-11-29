package com.mzielinski.advent.of.code.day13

import spock.lang.Specification
import spock.lang.Unroll

class Day13Test extends Specification {

    @Unroll
    def 'should calculate #result the earliest bus multiplied by wait time for file #filepath in part 1'() {
        expect:
        new Day13().findTheEarliestBusPart1(filepath) == result

        where:
        filepath       || result
        'day13/01.txt' || 295
        'day13/02.txt' || 102
    }

    @Unroll
    def 'should calculate #result the earliest bus multiplied by wait time for file #filepath in part 2'() {
        expect:
        new Day13().findTheEarliestBusPart2(filepath) == result

        where:
        filepath             || result
        'day13/01.txt'       || 1068781
        'day13/03.txt'       || 3417
        'day13/04.txt'       || 754018
        'day13/05.txt'       || 779210
        'day13/06.txt'       || 1261476
        'day13/07.txt'       || 1202161486
        'day13/02-part2.txt' || 327300950120029
    }
}