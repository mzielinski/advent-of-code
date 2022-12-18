package com.mzielinski.aoc2022.day18

class Day18 {

    fun boilingBoulders(lines: List<String>, part: String): Int {
        val cubes: List<Triple<Int, Int, Int>> = loadCube(lines)
        return when (part) {
            "01" -> cubes.flatMap { neighbors(it) }.count { !cubes.contains(it) }
            "02" -> partTwo(cubes)
            else -> throw IllegalArgumentException("Part $part is not supported")
        }
    }

    private fun partTwo(cubes: List<Triple<Int, Int, Int>>): Int {
        val min: Triple<Int, Int, Int> = findMin(cubes)
        val max: Triple<Int, Int, Int> = findMax(cubes)
        val queue = ArrayDeque<Triple<Int, Int, Int>>().also { it += min }
        val visited = mutableSetOf(min)
        while (queue.isNotEmpty()) {
            val cube = queue.removeFirst()
            visited += cube
            neighbors(cube)
                .filterNot(cubes::contains)
                .filterNot(visited::contains)
                .forEach { point ->
                    if (accept(point, min, max)) {
                        queue += point
                        visited += point
                    }
                }
        }
        return cubes.sumOf { neighbors(it).count(visited::contains) }
    }

    private fun findMin(cubes: List<Triple<Int, Int, Int>>): Triple<Int, Int, Int> {
        return Triple(cubes.minOf { it.first } - 1,
            cubes.minOf { it.second } - 1,
            cubes.minOf { it.third } - 1)
    }

    private fun findMax(cubes: List<Triple<Int, Int, Int>>): Triple<Int, Int, Int> {
        return Triple(cubes.maxOf { it.first } + 1,
            cubes.maxOf { it.second } + 1,
            cubes.maxOf { it.third } + 1)
    }

    private fun accept(point: Triple<Int, Int, Int>, min: Triple<Int, Int, Int>, max: Triple<Int, Int, Int>): Boolean {
        return point.first in min.first..max.first
                && point.second in min.second..max.second
                && point.third in min.third..max.third
    }

    private fun loadCube(lines: List<String>): List<Triple<Int, Int, Int>> {
        return lines.map { line: String ->
            val coordinates: List<String> = line.split(",")
            Triple(coordinates[0].toInt(), coordinates[1].toInt(), coordinates[2].toInt())
        }.toList()
    }

    private fun neighbors(current: Triple<Int, Int, Int>): Set<Triple<Int, Int, Int>> = buildSet {
        for (i in listOf(-1, 1)) {
            this.add(Triple(current.first + i, current.second, current.third))
            this.add(Triple(current.first, current.second + i, current.third))
            this.add(Triple(current.first, current.second, current.third + i))
        }
    }
}