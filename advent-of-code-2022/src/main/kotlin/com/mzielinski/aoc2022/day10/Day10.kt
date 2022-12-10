package com.mzielinski.aoc2022.day10

class Day10 {

    private val indexes: List<Int> = listOf(20, 60, 100, 140, 180, 220)

    fun cathodeRayTube(lines: List<String>, part: String): Int {
        val cycles: MutableList<Int> = buildCycles(lines)

        return when (part) {
            "01" -> part01(cycles)
            "02" -> part02(cycles)
            else -> -1
        }
    }

    private fun part01(cycles: MutableList<Int>) = indexes.sumOf { it * cycles[it - 1] }

    private fun buildCycles(lines: List<String>): MutableList<Int> {
        val cycles: MutableList<Int> = mutableListOf()
        var currentValue = 1

        lines.forEach { line ->
            val command = line.split(" ")

            when (command[0]) {
                "noop" -> {
                    cycles.add(currentValue)
                }

                "addx" -> {
                    cycles.add(currentValue)
                    cycles.add(currentValue)
                    currentValue += command[1].toInt()
                }
            }
        }
        return cycles
    }

    private fun part02(cycles: MutableList<Int>): Int {
        cycles.asSequence()
            .chunked(40).forEach { line ->
                val crt: MutableList<String> = mutableListOf()
                line.forEach {
                    if (it >= crt.size - 1 && it <= crt.size + 1) {
                        crt.add("#")
                    } else {
                        crt.add(".")
                    }
                }
                println(crt.joinToString(""))
            }
        println()
        return 0
    }
}