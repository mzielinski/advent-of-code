package com.mzielinski.aoc2022.day08

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day08Test {

    private val day08: Day08 = Day08()

    @Test
    fun part01() {
        assertEquals(21, day08.treetopTreeHouse(readFile("/day08/00.txt"), "01"))
        assertEquals(1835, day08.treetopTreeHouse(readFile("/day08/01.txt"), "01"))
    }

    @Test
    fun part02() {
        assertEquals(8, day08.treetopTreeHouse(readFile("/day08/00.txt"), "02"))
        assertEquals(263670, day08.treetopTreeHouse(readFile("/day08/01.txt"), "02"))
    }
}

