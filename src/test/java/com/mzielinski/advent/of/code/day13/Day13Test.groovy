package com.mzielinski.advent.of.code.day13

import spock.lang.Specification
import spock.lang.Unroll

class Day13Test extends Specification {

    @Unroll
    def 'should calculate #result the earliest bus multiplied by wait time for file #filepath'() {
        when:
        def day13 = new Day13()

        then:
        day13.findTheEarliestBus(filepath) == result

        where:
        filepath       || result
        'day13/01.txt' || 295
        'day13/02.txt' || 102
    }
}