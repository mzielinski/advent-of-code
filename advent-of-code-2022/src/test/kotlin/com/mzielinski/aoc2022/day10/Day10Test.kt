package com.mzielinski.aoc2022.day10

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day10Test {

    private val day10: Day10 = Day10()

    @Test
    fun part01() {
        assertEquals(13140, day10.cathodeRayTube(readFile("/day10/01.txt"), "01"))
        assertEquals(13520, day10.cathodeRayTube(readFile("/day10/02.txt"), "01"))
    }

    @Test
    fun part02() {
        assertEquals(0, day10.cathodeRayTube(readFile("/day10/01.txt"), "02"))
        // PGPHBEAB
        assertEquals(0, day10.cathodeRayTube(readFile("/day10/02.txt"), "02"))
    }
}