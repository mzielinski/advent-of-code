package com.mzielinski.aoc2022.day18

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day18Test {

    private val day18: Day18 = Day18()

    @Test
    fun part01() {
        assertEquals(10, day18.boilingBoulders(readFile("/day18/00.txt"), "01"))
        assertEquals(64, day18.boilingBoulders(readFile("/day18/01.txt"), "01"))
        assertEquals(3610, day18.boilingBoulders(readFile("/day18/02.txt"), "01"))
    }

    @Test
    fun part02() {
        assertEquals(58, day18.boilingBoulders(readFile("/day18/01.txt"), "02"))
        assertEquals(2082, day18.boilingBoulders(readFile("/day18/02.txt"), "02"))
    }
}