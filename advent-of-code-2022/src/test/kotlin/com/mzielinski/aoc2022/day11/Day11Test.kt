package com.mzielinski.aoc2022.day11

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day11Test {

    private val day11: Day11 = Day11()

    @Test
    fun part01() {
        assertEquals(10605, day11.monkeyInTheMiddle(readFile("/day11/00.txt")))
        assertEquals(102399, day11.monkeyInTheMiddle(readFile("/day11/01.txt")))
    }
}