package com.mzielinski.advent.of.code.day16

import spock.lang.Specification

import java.util.regex.Pattern

class Day16Test extends Specification {

    def 'should return #result as a sum all invalid numbers in tickets #ticket'() {
        expect:
        new Day16().countInvalidTickets(ticket) == result

        where:
        ticket         || result
        'day16/01.txt' || 71
        'day16/02.txt' || 30869
    }

    def 'should find out number position for each element in your ticket #ticket'() {
        expect:
        new Day16().multiplyValuesFromMyTickets(ticket, Pattern.compile(pattern)) == result

        where:
        ticket         | pattern        || result
        'day16/03.txt' | '(class|seat)' || 156
        'day16/02.txt' | 'departure.+'  || 4381476149273
    }
}
