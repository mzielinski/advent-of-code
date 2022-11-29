package com.mzielinski.advent.of.code.day18

import spock.lang.Specification
import spock.lang.Unroll

class Day18Test extends Specification {

    @Unroll
    def 'should calculate #result for math equation #equation'() {
        expect:
        new Day18.EquationResolver().calculate(equation) == result

        where:
        equation                                          || result
        '1 + 2 * 3 + 4 * 5 + 6'                           || 71
        '1 + (2 * 3) + (4 * (5 + 6))'                     || 51
        '2 * 3 + (4 * 5)'                                 || 26
        '5 + (8 * 3 + 9 + 3 * 4 * 3)'                     || 437
        '5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))'       || 12240
        '((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2' || 13632
    }

    @Unroll
    def 'should calculate #result for math equation #equation when addition have priority'() {
        expect:
        new Day18.EquationResolverAdditionFirst().calculate(equation) == result

        where:
        equation                                          || result
        '1 + 2 * 3 + 4 * 5 + 6'                           || 231
        '1 + (2 * 3) + (4 * (5 + 6))'                     || 51
        '2 * 3 + (4 * 5)'                                 || 46
        '5 + (8 * 3 + 9 + 3 * 4 * 3)'                     || 1445
        '5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))'       || 669060
        '((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2' || 23340
    }

    @Unroll
    def 'should sum all results from homework for filePath #filePath and equation resolver #equationResolver'() {
        expect:
        new Day18().sumAllResults(filePath, equationResolver) == result

        where:
        equationResolver                          | filePath       || result
        new Day18.EquationResolver()              | 'day18/02.txt' || 13976444272545
        new Day18.EquationResolverAdditionFirst() | 'day18/02.txt' || 88500956630893
    }
}
