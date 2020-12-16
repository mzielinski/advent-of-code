package com.mzielinski.advent.of.code.day16

import spock.lang.Specification

class Day16Test extends Specification {

    def 'should return #result as a sum all invalid numbers in tickets #ticket'() {
        expect:
        new Day16().countInvalidNumberInTickets(ticket) == result

        where:
        ticket         || result
        'day16/01.txt' || 71
        'day16/02.txt' || 30869
    }
}
