package com.mzielinski.aoc2022.day05

import com.mzielinski.aoc2022.GodUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day05Test {

    private val day05: Day05 = Day05()

    @Test
    fun part01() {
        Assertions.assertEquals("CMZ", day05.stacks(GodUtil.readFile("/day05/00.txt"), "01"))
        Assertions.assertEquals("RNZLFZSJH", day05.stacks(GodUtil.readFile("/day05/01.txt"), "01"))
    }

    @Test
    fun part02() {
        Assertions.assertEquals("MCD", day05.stacks(GodUtil.readFile("/day05/00.txt"), "02"))
        Assertions.assertEquals("CNSFCGJSM", day05.stacks(GodUtil.readFile("/day05/01.txt"), "02"))
    }
}