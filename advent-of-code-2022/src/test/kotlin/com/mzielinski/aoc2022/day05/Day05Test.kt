package com.mzielinski.aoc2022.day05

import com.mzielinski.aoc2022.GodUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day05Test {

    private val day05: Day05 = Day05()

    @Test
    fun part01() {
        Assertions.assertEquals(2, day05.todo(GodUtil.readFile("/day05/00.txt")))
    }

    @Test
    fun part02() {
    }
}