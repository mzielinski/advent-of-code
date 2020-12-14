package com.mzielinski.advent.of.code.day14


import spock.lang.Specification
import spock.lang.Unroll

class Day14Test extends Specification {

    @Unroll
    def 'should sum of address in memory for file #filepath'() {
        expect:
        new Day14().sumAllAddresses(filepath) == result

        where:
        filepath       || result
        'day14/01.txt' || 165
        'day14/02.txt' || 10452688630537
    }
}
