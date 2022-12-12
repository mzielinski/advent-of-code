package com.mzielinski.aoc2022.day12

import java.util.*

class Day12 {

    data class Point(val y: Int, val x: Int, val count: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Point

            if (y != other.y) return false
            if (x != other.x) return false

            return true
        }

        override fun hashCode(): Int {
            var result = y
            result = 31 * result + x
            return result
        }
    }

    fun hillClimbingAlgorithm(input: List<String>, part: String): Int {

        val visited = mutableSetOf<Point>()

        // start
        val queue: Queue<Point> = LinkedList()
        queue.addAll(findStart(input, part))

        // go thru graph
        while (queue.isNotEmpty()) {
            val point = queue.poll()
            if (point in visited) continue
            visited += point
            if (input[point.y][point.x] == 'E') {
                return point.count
            }
            findAvailableNeighbours(point, input, queue)
        }

        return -1
    }

    private fun findAvailableNeighbours(current: Point, input: List<String>, queue: Queue<Point>) {
        // up
        var nextPoint = pointAvailable(input, current.y - 1, current.x, current)
        if (nextPoint != null) {
            queue.add(nextPoint)
        }

        // right
        nextPoint = pointAvailable(input, current.y, current.x + 1, current)
        if (nextPoint != null) {
            queue.add(nextPoint)
        }

        // down
        nextPoint = pointAvailable(input, current.y + 1, current.x, current)
        if (nextPoint != null) {
            queue.add(nextPoint)
        }

        // left
        nextPoint = pointAvailable(input, current.y, current.x - 1, current)
        if (nextPoint != null) {
            queue.add(nextPoint)
        }
    }

    private fun pointAvailable(input: List<String>, nextY: Int, nextX: Int, currentPoint: Point): Point? {
        return if (nextY in input.indices && nextX in input[0].indices) {
            val nextValue = calculateValue(input[nextY][nextX])
            val currentValue = calculateValue(input[currentPoint.y][currentPoint.x])
            if (nextValue - currentValue < 2) return Point(nextY, nextX, currentPoint.count + 1)
            else null
        } else null
    }

    private fun calculateValue(value: Char): Char {
        return when (value) {
            'S' -> 'a'
            'E' -> 'z'
            else -> value
        }
    }

    private fun findStart(input: List<String>, part: String): List<Point> {
        val startingPoints:MutableList<Point> = mutableListOf()
        input.forEachIndexed { y, list ->
            list.forEachIndexed { x, char ->
                when(part) {
                    "01" -> if (char == 'S') startingPoints.add(Point(y, x, 0))
                    "02" -> if (char == 'a') startingPoints.add(Point(y, x, 0))
                }
            }
        }
        return startingPoints.toList()
    }
}