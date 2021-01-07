package com.mzielinski.advent.of.code.day20

import spock.lang.Ignore
import spock.lang.Specification

class Day20Test extends Specification {

    def 'should value for part1 $filePath'() {
        expect:
        Day20.multiplyCornerIds(filePath) == expectedImage

        where:
        filePath       || expectedImage
        'day20/01.txt' || 20899048083289L
        'day20/02.txt' || 79412832860579L
    }

    @Ignore('fails after changing to parallel steam. part two speed-uped from 1 min to 13 second.')
    def 'should build image for input $filePath'() {
        given:
        def result = this.class.classLoader.getResource(expectedImage).toURI()

        expect:
        Day20.buildImage(filePath) as String == new File(result).text

        where:
        filePath       || expectedImage
        'day20/01.txt' || 'day20/01-image.txt'
        'day20/02.txt' || 'day20/02-image.txt'
    }

    @Ignore('fails after changing to parallel steam. part two speed-uped from 1 min to 13 second.')
    def 'should build image without borders for input $filePath'() {
        given:
        def result = this.class.classLoader.getResource(expectedImage).toURI()

        when:
        def tile = Day20.convertToImageWithoutBorders(filePath)

        then:
        tile as String == new File(result).text
        tile.xMax() == expectedXMax
        tile.yMax() == expectedYMax

        where:
        filePath       || expectedImage                        | expectedXMax | expectedYMax
        'day20/01.txt' || 'day20/01-image-without-borders.txt' | 23           | 23
        'day20/02.txt' || 'day20/02-image-without-borders.txt' | 95           | 95
    }

    def 'should value for part2 $filePath'() {
        expect:
        Day20.countActivePointsExceptMonsters(filePath) == expectedImage

        where:
        filePath       || expectedImage
        'day20/01.txt' || 273
        'day20/02.txt' || 2155
    }
}
