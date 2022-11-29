package com.mzielinski.advent.of.code.day09

import spock.lang.Specification
import spock.lang.Unroll

class Day09Test extends Specification {

    @Unroll
    def 'should find first invalid number #result when path is #path, preamble #preamble'() {
        expect:
        new Day09().findFirstInvalidNumber(path, preamble) == result

        where:
        path           | preamble || result
        'day09/01.txt' | 5         | 127
        'day09/02.txt' | 25        | 1639024365
    }

    @Unroll
    def 'should find encryption weakness #result when path is #path, searchedNumber #searchedNumber'() {
        expect:
        new Day09().findEncryptionWeakness(path, searchedNumber) == result

        where:
        path           | searchedNumber || result
        'day09/01.txt' | 127             | 62
        'day09/02.txt' | 1639024365      | 219202240
    }
}
