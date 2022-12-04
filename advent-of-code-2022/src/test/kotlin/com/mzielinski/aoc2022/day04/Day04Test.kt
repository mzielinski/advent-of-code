package com.mzielinski.aoc2022.day04

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day04Test {

    private val day04: Day04 = Day04()

    @Test
    fun part01() {
        assertEquals(2, day04.findDuplicates(readFile("/day04/00.txt"), "01"))
        assertEquals(599, day04.findDuplicates(readFile("/day04/01.txt"), "01"))
    }

    @Test
    fun part02() {
        assertEquals(4, day04.findDuplicates(readFile("/day04/00.txt"), "02"))
        assertEquals(928, day04.findDuplicates(readFile("/day04/01.txt"), "02"))
    }
}