package com.mzielinski.aoc2022.day08

class Day08 {

    fun treetopTreeHouse(lines: List<String>, part: String): Int {

        val map: List<List<Int>> = lines.map { line: String ->
            line.split("")
                .filter { it != "" }
                .map { it.toInt() }
                .toList()
        }

        return when (part) {
            "01" -> {
                val trees: List<List<Boolean>> = map.mapIndexed { y: Int, rows: List<Int> ->
                    List(rows.size) { x: Int ->
                        val checkUp = checkUp(y, x, map)
                        val checkDown = checkDown(y, x, map)
                        val checkLeft = checkLeft(y, x, map)
                        val checkRight = checkRight(y, x, map)
                        checkUp || checkDown || checkLeft || checkRight
                    }
                }
                trees.flatten().count { it }
            }

            "02" -> {
                val trees: List<List<Int>> = map.mapIndexed { y: Int, rows: List<Int> ->
                    List(rows.size) { x: Int ->
                        if (x == 0 || y == 0 || x == rows.size - 1 || y == map.size - 1) 0
                        else {
                            val checkUp = checkUpCount(y, x, map)
                            val checkDown = checkDownCount(y, x, map)
                            val checkLeft = checkLeftCount(y, x, map)
                            val checkRight = checkRightCount(y, x, map)
                            checkUp * checkDown * checkLeft * checkRight
                        }
                    }
                }
                trees.flatten().maxOf { it }
            }

            else -> -1
        }
    }

    private fun checkLeft(y: Int, x: Int, map: List<List<Int>>): Boolean {
        return if (y == 0 || x == 0) true
        else map[y].subList(0, x).all {
            it < map[y][x]
        }
    }

    private fun checkRightCount(y: Int, x: Int, map: List<List<Int>>): Int {
        val range = map[y].subList(0, x).reversed()
        return count(range) { it < map[y][x] }
    }

    private fun checkRight(y: Int, x: Int, map: List<List<Int>>): Boolean {
        return if (y == map.size - 1 || x == map[y].size - 1) true
        else map[y].subList(x + 1, map[y].size).all {
            it < map[y][x]
        }
    }

    private fun checkLeftCount(y: Int, x: Int, map: List<List<Int>>): Int {
        val range = map[y].subList(x + 1, map[y].size)
        return count(range) { it < map[y][x] }
    }

    private fun checkDown(y: Int, x: Int, map: List<List<Int>>): Boolean {
        return (y + 1 until map[y].size).all {
            map[it][x] < map[y][x]
        }
    }

    private fun checkDownCount(y: Int, x: Int, map: List<List<Int>>): Int {
        val range = (y + 1 until map[y].size).toList()
        return count(range) { map[it][x] < map[y][x] }
    }

    private fun checkUp(y: Int, x: Int, map: List<List<Int>>): Boolean {
        return (0 until y).all {
            map[it][x] < map[y][x]
        }
    }

    private fun checkUpCount(y: Int, x: Int, map: List<List<Int>>): Int {
        val range = (0 until y).reversed().toList()
        return count(range) { map[it][x] < map[y][x] }
    }

    private fun count(range: List<Int>, function: (Int) -> Boolean): Int {
        var count = 0
        range.forEach {
            if (function.invoke(it)) {
                count++
            } else {
                return ++count
            }
        }
        return count
    }
}

