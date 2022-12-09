package com.mzielinski.aoc2022.day09

import kotlin.math.abs

class Day09 {

    data class Point(val y: Int, val x: Int) {

        private val nextToList = listOf(0, 1)

        fun isNextTo(point: Point): Boolean {
            return nextToList.contains(abs(x - point.x)) && nextToList.contains(abs(y - point.y))
        }

        override fun toString(): String {
            return "(y=$y, x=$x)"
        }
    }

    class Position(private val head: Point, val tail: Point) {

        fun moveRight(): Position {
            val newHead = Point(head.y, head.x + 1)
            return Position(newHead, calculateTail(newHead, "right"))
        }

        fun moveLeft(): Position {
            val newHead = Point(head.y, head.x - 1)
            return Position(newHead, calculateTail(newHead, "left"))
        }

        fun moveUp(): Position {
            val newHead = Point(head.y + 1, head.x)
            return Position(newHead, calculateTail(newHead, "up"))
        }

        fun moveDown(): Position {
            val newHead = Point(head.y - 1, head.x)
            return Position(newHead, calculateTail(newHead, "down"))
        }

        private fun calculateTail(newHead: Point, direction: String): Point {
            return if (newHead.isNextTo(tail)) {
                tail
            } else {
                when (direction) {
                    "right" -> Point(newHead.y, newHead.x - 1)
                    "left" -> Point(newHead.y, newHead.x + 1)
                    "up" -> Point(newHead.y - 1, newHead.x)
                    "down" -> Point(newHead.y + 1, newHead.x)
                    else -> throw IllegalArgumentException("Invalid direction $direction")
                }
            }
        }

        override fun toString(): String {
            return "(H=$head, T=$tail)"
        }
    }

    fun ropeBridge(lines: List<String>): Int {

        var currentPosition = Position(Point(0, 0), Point(0, 0))
        val visitedPositions = mutableListOf<Position>()

        lines.forEach { line ->
            val command: List<String> = line.split(" ")
            val visitedPositionsAfterCommand: List<Position> = position(currentPosition, command[1].toInt()) {
                when (command[0]) {
                    "U" -> it.moveUp()
                    "D" -> it.moveDown()
                    "L" -> it.moveLeft()
                    "R" -> it.moveRight()
                    else -> throw IllegalArgumentException("Invalid input in line $line")
                }
            }
            visitedPositions.addAll(visitedPositionsAfterCommand)
            currentPosition = visitedPositionsAfterCommand.last()
        }

        return visitedPositions.map { it.tail }.toSet().size
    }

    private fun position(position: Position, steps: Int, function: (Position) -> Position): List<Position> {
        val visitedPoints = mutableListOf<Position>()
        var currect: Position = position
        repeat((0 until steps).count()) {
            currect = function.invoke(currect)
            visitedPoints.add(currect)
        }
        return visitedPoints
    }
}