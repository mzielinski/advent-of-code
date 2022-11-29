package com.mzielinski.advent.of.code.day19

import spock.lang.Specification
import spock.lang.Unroll

class Day19Test extends Specification {

    public static final def DEFAULT_RULES = new Day19.RulesDefinition([
            '0': ['4,1,5'],
            '1': ['2,3', '3,2'],
            '2': ['4,4', '5,5'],
            '3': ['4,5', '5,4'],
            '4': ['a'],
            '5': ['b']])

    @Unroll
    def 'should check message #message against provided rules'() {
        given:
        def resolver = new RulesResolver(DEFAULT_RULES)

        when:
        Day19.Rules rules = resolver.convertToRules()

        then:
        new Day19(rules).matchAgainstRules(message) == result

        where:
        message   || result
        'ababbb'  || true
        'bababa'  || false
        'abbbab'  || true
        'aaabbb'  || false
        'aaaabbb' || false
    }

    @Unroll
    def 'should read init file #filePath'() {
        expect:
        RulesReader.parseRules(filePath) == expectedResult

        where:
        filePath       || expectedResult
        'day19/01.txt' || DEFAULT_RULES
    }

    def 'should calculate how many messages are valid for main rule'() {
        given:
        def rulesDefinitions = RulesReader.parseRules(filePath)
        def messages = MessagesReader.parseMessages(filePath)
        def max = messages.collect { it.length() }.max()
        def rules = new RulesResolver(rulesDefinitions).convertToRules(max * 2 - 1)

        expect:
        new Day19(rules).calculateValidMessages(messages) == expectedResult

        where:
        filePath       || expectedResult
        'day19/01.txt' || 2
//        'day19/02.txt' || 102
    }
}
