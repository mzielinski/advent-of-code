package com.mzielinski.aoc2022.day01

import com.mzielinski.aoc2022.GodUtil.readFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val day01: Day01 = Day01()

    @Test
    fun part01() {
        assertEquals(24000, day01.findMostCalories(readFile("/day01/00.txt")))
        assertEquals(66186, day01.findMostCalories(readFile("/day01/01.txt")))
    }

    @Test
    fun part02() {
        assertEquals(45000, day01.findMostCalories(readFile("/day01/00.txt"), 3))
        assertEquals(196804, day01.findMostCalories(readFile("/day01/01.txt"), 3))

    }
}