package com.mzielinski.advent.of.code.day19

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

class Day19Test extends Specification {

    public static final Map<String, Set<List<String>>> DEFAULT_RULES = [
            '0': Set.of(['4', '1', '5']),
            '1': Set.of(['2', '3'], ['3', '2']),
            '2': Set.of(['4', '4'], ['5', '5']),
            '3': Set.of(['4', '5'], ['5', '4']),
            '4': Set.of(['a']),
            '5': Set.of(['b'])
    ]

    @Ignore('Not working... in progress')
    @Unroll
    def 'should check message #message against provided rules'() {
        given:
        def day19 = new Day19(DEFAULT_RULES)

        when:
        Day19.Rules rules = day19.convertToRules()

        then:
        day19.matchAgainstRules(message, rules.rules().get('0')) == result

        where:
        message   || result
        'ababbb'  || true
        'bababa'  || false
        'abbbab'  || true
        'aaabbb'  || false
        'aaaabbb' || false
    }

}
