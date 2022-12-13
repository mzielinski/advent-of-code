package com.mzielinski.aoc2022.day13

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day13Test {

    private val day13: Day13 = Day13()

    @Test
    fun part01() {
        assertEquals(13, day13.distressSignal01(readFile("/day13/00.txt")))
        assertEquals(5605, day13.distressSignal01(readFile("/day13/01.txt")))
    }

    @Test
    fun part02() {
        assertEquals(140, day13.distressSignal02(readFile("/day13/00.txt")))
        assertEquals(24969, day13.distressSignal02(readFile("/day13/01.txt")))
    }

}