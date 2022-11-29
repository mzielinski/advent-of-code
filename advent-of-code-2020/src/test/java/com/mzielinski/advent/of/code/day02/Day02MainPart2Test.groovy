package com.mzielinski.advent.of.code.day02

import spock.lang.Specification

class Day02MainPart2Test extends Specification {

    def 'should check test vector'() {
        def out = new Day02MainPart2()

        def inputs = [new Day02MainPart2.Input(firstIndex: 1, secondIndex: 3, searchedChar: 'c' as char, password: 'abcde'),
                      new Day02MainPart2.Input(firstIndex: 1, secondIndex: 3, searchedChar: 'b' as char, password: 'cdefg'),
                      new Day02MainPart2.Input(firstIndex: 2, secondIndex: 9, searchedChar: 'c' as char, password: 'ccccccccc')]

        expect:
        out.calculate(inputs) == 1
    }
}
