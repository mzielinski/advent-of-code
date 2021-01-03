package com.mzielinski.advent.of.code.day23

import spock.lang.Specification
import spock.lang.Unroll

class Day23Test extends Specification {

    @Unroll
    def 'should calculate cub circle after #moves moves and after cub no. #cub'() {
        given:
        def cubs = new Day23().moveCubs(moves, circle)

        expect:
        cubs.after(cub) == result

        where:
        cub | moves | circle                                        || result
        1   | 10    | new Day23.Circle([3, 8, 9, 1, 2, 5, 4, 6, 7]) || '92658374'
        1   | 100   | new Day23.Circle([3, 8, 9, 1, 2, 5, 4, 6, 7]) || '67384529'
        1   | 100   | new Day23.Circle([3, 1, 8, 9, 4, 6, 5, 7, 2]) || '52864379'
    }
}
