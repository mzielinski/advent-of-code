package com.mzielinski.aoc2022.day05

private const val EMPTY_ELEMENT = "_"

class Day05 {

    fun stacks(lines: List<String>, part: String): String {
        val stack: MutableList<ArrayDeque<String>> = mutableListOf()
        for (line in lines) {
            if (line == "") {
                // skip
            } else if (line.matches(Regex("move \\d+ from \\d+ to \\d+"))) {
                processCommand(line, stack, part)
            } else {
                convertToStack(line, stack)
            }
        }
        return stack.map { it.first() }.toList().joinToString("")
    }

    private fun convertToStack(line: String, stack: MutableList<ArrayDeque<String>>) {
        line.replace("[", "")
            .replace("]", "")
            .split(" ")
            .forEachIndexed { index, value ->
                if (stack.lastIndex < index) {
                    stack.add(index, ArrayDeque())
                }
                if (value != EMPTY_ELEMENT) {
                    val currentStack = stack[index]
                    currentStack.add(value)
                }
            }
    }

    private fun processCommand(line: String, stack: MutableList<ArrayDeque<String>>, part: String) {
        val moveResult: MatchResult = Regex("\\d+").find(line)!!
        val fromResult: MatchResult = moveResult.next()!!
        val toResult: MatchResult = fromResult.next()!!

        val listSource: ArrayDeque<String> = stack[fromResult.value.toInt() - 1]
        val listTarget: ArrayDeque<String> = stack[toResult.value.toInt() - 1]

        partLogic(listSource.take(moveResult.value.toInt()), part).forEach {
            listTarget.addFirst(it)
            listSource.removeFirst()
        }
    }

    private fun partLogic(source: List<String>, part: String): List<String> {
        return if (part == "02") source.reversed()
        else source
    }
}