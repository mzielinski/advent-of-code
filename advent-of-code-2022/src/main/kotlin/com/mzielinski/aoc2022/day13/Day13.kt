package com.mzielinski.aoc2022.day13

class Day13 {

    fun distressSignal01(lines: List<String>): Int {
        var result = 0
        lines.asSequence().chunked(3)
            .forEachIndexed { index: Int, input: List<String> ->
                val left = parse(input[0])
                val right = parse(input[1])
                if (compare(right, left) == 1) {
                    result += index + 1
                }
            }
        return result
    }

    fun distressSignal02(lines: List<String>): Int {
        val extendedList = lines.filter { it != "" } + listOf("[[2]]", "[[6]]")
        val sortedList = extendedList.sortedWith(this::compare)
        return (sortedList.indexOf("[[2]]") + 1) * (sortedList.indexOf("[[6]]") + 1)
    }

    private fun compare(left: Any, right: Any): Int {
        return when {
            left is String && right is String -> compare(parse(left), parse(right))
            left is Int && right is Int -> left.compareTo(right)
            left is Int -> compare(listOf(left), right)
            right is Int -> compare(left, listOf(right))
            left is List<*> && right is List<*> -> {
                for (i in 0 until minOf(left.size, right.size)) {
                    compare(left[i]!!, right[i]!!).let { if (it != 0) return it }
                }
                compare(left.size, right.size)
            }

            else -> throw IllegalArgumentException("No supported types for $left and $right")
        }
    }

    private fun parse(string: String): Any {
        if (string.matches(Regex("\\d+"))) {
            return string.toInt()
        }
        var count = 0
        var value = ""
        val list = mutableListOf<Any>()
        string.subSequence(1, string.lastIndex).forEach {
            if (it == '[') count++
            if (it == ']') count--
            if (count != 0 || it != ',') value += it
            else list += parse(value).also { value = "" }
        }
        if (value.isNotEmpty()) {
            list += parse(value)
        }
        return list
    }
}