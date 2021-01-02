package com.mzielinski.advent.of.code.day21

import spock.lang.Specification

class Day21Test extends Specification {

    def 'should calculate number of ingredients which are not at the list for #input'() {
        expect:
        new Day21().calculateNumberOfSaveIngredients(input) == result

        where:
        input          || result
        'day21/01.txt' || 5
        'day21/02.txt' || 2517
    }
}
