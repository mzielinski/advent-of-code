package com.mzielinski.aoc2022.day09

import com.mzielinski.aoc2022.GodUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test {

    private val day09: Day09 = Day09()

    @Test
    fun part01() {
        assertEquals(13, day09.ropeBridge(GodUtil.readFile("/day09/00.txt")))
        assertEquals(6357, day09.ropeBridge(GodUtil.readFile("/day09/01.txt")))
    }
}