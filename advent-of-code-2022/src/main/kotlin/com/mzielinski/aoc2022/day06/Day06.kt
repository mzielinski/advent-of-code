package com.mzielinski.aoc2022.day06

class Day06 {

    fun tuningTrouble(input: String, expectedSize: Int): Int {
        val arrayDeque = ArrayDeque<Char>()
        input.forEachIndexed { index, value ->
            if (arrayDeque.size < expectedSize) {
                arrayDeque.add(value)
            } else if (arrayDeque.size == expectedSize) {
                if (arrayDeque.toSet().size == expectedSize) {
                    return index
                } else {
                    arrayDeque.removeFirst()
                    arrayDeque.addLast(value)
                }
            }
        }
        return -1
    }
}