package com.mzielinski.aoc2022.day07

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day07Test {

    private val day07: Day07 = Day07()

    @Test
    fun part01() {
        assertEquals(95437, day07.noSpaceOnDevice(readFile("/day07/00.txt"), "01"))
        assertEquals(1297683, day07.noSpaceOnDevice(readFile("/day07/01.txt"), "01"))
    }

    @Test
    fun part02() {
        assertEquals(24933642, day07.noSpaceOnDevice(readFile("/day07/00.txt"), "02"))
        assertEquals(5756764, day07.noSpaceOnDevice(readFile("/day07/01.txt"), "02"))
    }
}

