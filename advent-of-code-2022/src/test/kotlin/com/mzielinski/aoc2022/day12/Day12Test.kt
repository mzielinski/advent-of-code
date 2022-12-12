package com.mzielinski.aoc2022.day12

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day12Test {

    private val day12: Day12 = Day12()

    @Test
    fun part01() {
        assertEquals(31, day12.hillClimbingAlgorithm(readFile("/day12/00.txt"), "01"))
        assertEquals(497, day12.hillClimbingAlgorithm(readFile("/day12/01.txt"), "01"))
    }

    @Test
    fun part02() {
        assertEquals(29, day12.hillClimbingAlgorithm(readFile("/day12/00.txt"), "02"))
        assertEquals(492, day12.hillClimbingAlgorithm(readFile("/day12/01.txt"), "02"))
    }
}