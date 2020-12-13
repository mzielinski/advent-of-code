package com.mzielinski.advent.of.code.day12

import spock.lang.Specification
import spock.lang.Unroll

class Day12Test extends Specification {

    @Unroll
    def 'should calculate #result as Manhattan distance for file #filepath and #part'() {
        when:
        def ferry = aoc.calculatePositionsOnMap(filepath)

        then:
        ferry.calculateManhattanDistance() == result

        where:
        part    | aoc           | filepath       || result
        'part1' | Day12.part1() | 'day12/01.txt' || 25
        'part1' | Day12.part1() | 'day12/02.txt' || 1589
        'part2' | Day12.part2() | 'day12/01.txt' || 286
        'part2' | Day12.part2() | 'day12/02.txt' || 23960
        'part2' | Day12.part2() | 'day12/03.txt' || 600
    }
}
