package com.mzielinski.aoc2022.day05

class Day05 {

    fun stacks(lines: List<String>, part: String): String {
        val stack: MutableList<ArrayDeque<String>> = mutableListOf()
        for (line in lines) {
            if (line == "") {
                // skip
            } else if (line.matches(Regex("move \\d+ from \\d+ to \\d+"))) {
                processCommands(line, stack, part)
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
                if (value != "_") {
                    val currentStack = stack[index]
                    currentStack.add(value)
                }
            }
    }

    private fun processCommands(line: String, stack: MutableList<ArrayDeque<String>>, part: String) {

        val moveResult = Regex("\\d+").find(line)
        val fromResult = moveResult?.next()
        val toResult = fromResult?.next()

        val count: Int = moveResult?.value?.toInt()!!
        val from: Int = fromResult?.value?.toInt()!! - 1
        val to: Int = toResult?.value?.toInt()!! - 1

        val listSource: ArrayDeque<String> = stack[from]
        val listTarget: ArrayDeque<String> = stack[to]

        reversIfPart02(listSource.take(count), part).forEach {
            listTarget.addFirst(it)
            listSource.removeFirst()
        }
    }

    private fun reversIfPart02(source: List<String>, part: String): List<String> {
        return if (part == "02") source.reversed()
        else source
    }
}