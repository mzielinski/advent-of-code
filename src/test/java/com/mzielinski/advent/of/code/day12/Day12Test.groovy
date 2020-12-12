package com.mzielinski.advent.of.code.day12

import com.mzielinski.advent.of.code.day12.model.Position
import spock.lang.Specification
import spock.lang.Unroll

class Day12Test extends Specification {

    def 'should calculate position on the map'() {
        given:
        Day12 objectUnderTest = new Day12()

        expect:
        objectUnderTest.calculatePositionsOnMap(filepath).position == expectedPosition

        where:
        filepath       || expectedPosition
        'day12/01.txt' || Position.of(0, 8, 17, 0)
    }

    @Unroll
    def 'should calculate Manhattan distance for #filepath'() {
        given:
        Day12 objectUnderTest = new Day12()

        when:
        def ferryMap = objectUnderTest.calculatePositionsOnMap(filepath)

        then:
        ferryMap.calculateManhattanDistance() == result

        where:
        filepath       || result
        'day12/01.txt' || 25
        'day12/02.txt' || 1589
    }
}
