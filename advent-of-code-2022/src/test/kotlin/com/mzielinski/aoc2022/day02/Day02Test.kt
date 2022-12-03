package com.mzielinski.aoc2022.day02

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day02Test {

    private val day02: Day02 = Day02()

    @Test
    fun part01() {
        assertEquals(15, day02.rockPaperScissors(readFile("/day02/00.txt"), "01"))
        assertEquals(15691, day02.rockPaperScissors(readFile("/day02/01.txt"), "01"))
    }

    @Test
    fun part02() {
        assertEquals(12, day02.rockPaperScissors(readFile("/day02/00.txt"), "02"))
        assertEquals(15691, day02.rockPaperScissors(readFile("/day02/01.txt"), "02"))
    }
}