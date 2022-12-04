package com.mzielinski.aoc2022.day03

class Day03 {

    fun compartments(lines: List<String>): Int {
        return lines.sumOf { line ->
            val first: MutableSet<Char> = line.substring(0, (line.length / 2)).toCharArray().toMutableSet()
            val second: MutableSet<Char> = line.substring((line.length / 2)).toCharArray().toMutableSet()
            first.retainAll(second)
            first.map { c -> c.toInt() }.sumOf { convertToValue(it) }
        }
    }

    fun compartmentsPart02(lines: List<String>): Int {
        return lines.asSequence().withIndex()
            .groupBy { it.index / 3 }
            .map { it.value.map { it.value } }
            .sumOf { line ->
                val first: MutableSet<Char> = line[0].toCharArray().toMutableSet()
                val second: MutableSet<Char> = line[1].toCharArray().toMutableSet()
                val three: MutableSet<Char> = line[2].toCharArray().toMutableSet()
                first.retainAll(second)
                first.retainAll(three)
                first.map { c -> c.toInt() }.sumOf { convertToValue(it) }
            }
    }

    private fun convertToValue(it: Int) = when (it) {
        in 97..122 -> it - 96
        in 65..90 -> it - 38
        else -> 0
    }
}