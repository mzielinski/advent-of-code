package com.mzielinski.advent.of.code.day23

import spock.lang.Specification
import spock.lang.Unroll

class Day23Test extends Specification {

    @Unroll
    def 'should calculate cup circle after #moves moves and after cup no. #mainCup'() {
        given:
        def circle = new Day23().moveCups(moves, new Day23.Circle(cups, 1, 9))

        expect:
        circle.printCircleAfter(mainCup) == result

        where:
        mainCup | moves | cups                        || result
        1       | 10    | [3, 8, 9, 1, 2, 5, 4, 6, 7] || '92658374'
        1       | 100   | [3, 8, 9, 1, 2, 5, 4, 6, 7] || '67384529'
        1       | 100   | [3, 1, 8, 9, 4, 6, 5, 7, 2] || '52864379'
    }

    @Unroll
    def 'should multiply two hidden stars after cup no. #mainCup after #moves and with #total cups'() {
        expect:
        new Day23().findHiddenTwoStars(moves, new Day23.Circle(cups, 1, total), total, mainCup) == result

        where:
        mainCup | moves    | cups                        | total   || result
        1       | 100      | [3, 8, 9, 1, 2, 5, 4, 6, 7] | 9       || 42
        1       | 10000000 | [3, 8, 9, 1, 2, 5, 4, 6, 7] | 1000000 || 149245887792
        1       | 10000000 | [3, 1, 8, 9, 4, 6, 5, 7, 2] | 1000000 || 11591415792
    }
}
