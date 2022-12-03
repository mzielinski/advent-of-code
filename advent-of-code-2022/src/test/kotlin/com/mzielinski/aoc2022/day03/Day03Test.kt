package com.mzielinski.aoc2022.day03

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day03Test {

    private val day03: Day03 = Day03()

    @Test
    fun part01() {
        assertEquals(157, day03.compartments(readFile("/day03/00.txt")))
        assertEquals(7553, day03.compartments(readFile("/day03/01.txt")))
    }

    @Test
    fun part02() {
        assertEquals(70, day03.compartmentsPart02(readFile("/day03/00.txt")))
        assertEquals(2758, day03.compartmentsPart02(readFile("/day03/01.txt")))
    }
}