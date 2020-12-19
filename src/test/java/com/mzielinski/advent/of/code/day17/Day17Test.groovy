package com.mzielinski.advent.of.code.day17

import com.mzielinski.advent.of.code.day17.geometry.d3.Point3D
import spock.lang.Specification
import spock.lang.Unroll

class Day17Test extends Specification {

    def 'should verify that initial cube is created properly'() {
        given:
        Grid grid = Grid.initializeCube('day17/01.txt', Day17.point3DCubeReader)

        expect:
        grid.activePoints().contains(new Point3D(0, 0, 1))
        grid.activePoints().contains(new Point3D(0, 1, 2))
        grid.activePoints().contains(new Point3D(0, 2, 0))
        grid.activePoints().contains(new Point3D(0, 2, 1))
        grid.activePoints().contains(new Point3D(0, 2, 2))

        and:
        grid.activePoints().size() == 5
    }

    def 'should verify that cube is changed properly to next stage for single round in 03.txt'() {
        given:
        Grid grid = Grid.initializeCube('day17/03.txt', Day17.point3DCubeReader)

        when:
        def round = new Day17(Day17.point3DNeighboursGenerator, Day17.point3DCubeReader).performSingleRound(grid)

        then:
        grid.activePoints().size() == 3

        and:
        round.activePoints().contains(new Point3D(-1, 1, 1))
        round.activePoints().contains(new Point3D(0, 1, 1))
        round.activePoints().contains(new Point3D(1, 1, 1))
    }

    def 'should verify that cube is changed properly to next stage for single round in 01.txt'() {
        given:
        Grid grid = Grid.initializeCube('day17/01.txt', Day17.point3DCubeReader)

        when:
        def round = new Day17(Day17.point3DNeighboursGenerator, Day17.point3DCubeReader).performSingleRound(grid)

        then:
        round.activePoints().size() == 11

        // z: -1
        round.activePoints().contains(new Point3D(-1, 1, 0))
        round.activePoints().contains(new Point3D(-1, 2, 2))
        round.activePoints().contains(new Point3D(-1, 3, 1))

        // z: 0

        round.activePoints().contains(new Point3D(0, 1, 0))
        round.activePoints().contains(new Point3D(0, 1, 2))
        round.activePoints().contains(new Point3D(0, 2, 1))
        round.activePoints().contains(new Point3D(0, 2, 2))
        round.activePoints().contains(new Point3D(0, 3, 1))

        // z: 1
        round.activePoints().contains(new Point3D(1, 1, 0))
        round.activePoints().contains(new Point3D(1, 2, 2))
        round.activePoints().contains(new Point3D(1, 3, 1))
    }

    @Unroll
    def 'should find #result active pieces for #filePath after #cycle boot process'() {
        expect:
        new Day17(generator, cubeReader).countActivePieces(filePath, cycle) == result

        where:
        generator                        | cubeReader              | filePath       | cycle || result
        Day17.point3DNeighboursGenerator | Day17.point3DCubeReader | 'day17/01.txt' | 6     || 112
        Day17.point3DNeighboursGenerator | Day17.point3DCubeReader | 'day17/02.txt' | 6     || 315
        Day17.point4DNeighboursGenerator | Day17.point4DCubeReader | 'day17/01.txt' | 6     || 848
        Day17.point4DNeighboursGenerator | Day17.point4DCubeReader | 'day17/02.txt' | 6     || 1520
    }
}
