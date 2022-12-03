package com.mzielinski.aoc2022.day01

class Day01 {

    fun findMostCalories(data: List<String>): Int {
        return calculateCalories(data).first()
    }

    fun findMostCalories(data: List<String>, size: Int): Int {
        return calculateCalories(data).subList(0, size)
            .reduce { acc, value -> acc + value }
    }

    private fun calculateCalories(data: List<String>): List<Int> {
        val elves = ArrayList<Int>()
        var current = 0
        val itr = data.listIterator()
        while (itr.hasNext()) {
            val next = itr.next()
            if (next == "" || next.isEmpty()) {
                elves += current
                current = 0
            } else {
                current += next.toInt()
            }
        }
        elves.sortDescending()
        return elves
    }
}