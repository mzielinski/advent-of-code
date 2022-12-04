package com.mzielinski.aoc2022.day04

class Day04 {

    fun findDuplicates(lines: List<String>, part: String): Int {
        return lines.sumOf {
            val line = it.split(",")
            val list1 = extracted(line[0])
            val list2 = extracted(line[1])
            logic(part, list1, list2)
        }
    }

    private fun logic(part: String, list1: List<Int>, list2: List<Int>): Int =
        if (part == "01" && logicPart1(list1, list2)) 1
        else if (part == "02" && logicPart2(list1, list2)) 1
        else 0

    private fun logicPart1(list1: List<Int>, list2: List<Int>) =
        list1.containsAll(list2) || list2.containsAll(list1)

    private fun logicPart2(list1: List<Int>, list2: List<Int>): Boolean =
        list1.any { it in list2 } || list2.any { it in list1 }

    private fun extracted(input: String): List<Int> {
        val min1: Int = input.split("-")[0].toInt()
        val max1: Int = input.split("-")[1].toInt()
        return arrayOf(min1..max1)[0].toList()
    }
}