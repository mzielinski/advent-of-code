package com.mzielinski.advent.of.code.day14

import spock.lang.Specification
import spock.lang.Unroll

class Day14Test extends Specification {

    @Unroll
    def 'should sum of address in memory for file #filepath'() {
        expect:
        new Day14().solvePuzzle(filepath, puzzle) == result

        where:
        puzzle    | filepath       || result
        'puzzle1' | 'day14/01.txt' || 165
        'puzzle1' | 'day14/02.txt' || 10452688630537
        'puzzle1' | 'day14/04.txt' || 119764755271
        'puzzle1' | 'day14/05.txt' || 4967470085
        'puzzle2' | 'day14/03.txt' || 208
        'puzzle2' | 'day14/04.txt' || 123280185200
        'puzzle2' | 'day14/05.txt' || 75237376
        'puzzle2' | 'day14/02.txt' || 2881082759597
    }
}
