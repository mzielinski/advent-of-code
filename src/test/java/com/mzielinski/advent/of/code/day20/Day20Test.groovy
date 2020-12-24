package com.mzielinski.advent.of.code.day20

import spock.lang.Specification

class Day20Test extends Specification {

    def 'should find image for input $filePath'() {
        expect:
        Day20.multiplyCornerIds(filePath) == expectedImage

        where:
        filePath       || expectedImage
        'day20/01.txt' || 20899048083289L
        'day20/02.txt' || 79412832860579L
    }
}
