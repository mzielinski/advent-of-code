package com.mzielinski.advent.of.code.day21

import spock.lang.Specification
import spock.lang.Unroll

class Day21Test extends Specification {

    @Unroll
    def 'should calculate number of ingredients which are not at the list for #input'() {
        expect:
        new Day21().calculateNumberOfSaveIngredients(input) == result

        where:
        input          || result
        'day21/01.txt' || 5
        'day21/02.txt' || 2517
    }

    @Unroll
    def 'should find canonical dangerous ingredients list for #input'() {
        expect:
        new Day21().findCanonicalDangerousIngredients(input) == result

        where:
        input          || result
        'day21/01.txt' || 'mxmxvkd,sqjhc,fvjkl'
        'day21/02.txt' || 'rhvbn,mmcpg,kjf,fvk,lbmt,jgtb,hcbdb,zrb'
    }
}
