package com.mzielinski.aoc2022.day11

class Day11 {

    class Monkey {

        var id: Int = -1
        val items: MutableList<Int> = mutableListOf()
        lateinit var operation: (Int) -> Int
        lateinit var test: (Int) -> Boolean
        var ifTrueMonkeyId: Int = -1
        var ifFalseMonkeyId: Int = -1

        fun withId(id: Int) {
            this.id = id
        }

        fun addItem(item: Int) {
            this.items.add(item)
        }

        fun withStartingItems(startingItems: List<Int>) {
            this.items.addAll(startingItems)
        }

        fun withOperation(operation: (Int) -> Int) {
            this.operation = operation
        }

        fun withTest(test: (Int) -> Boolean) {
            this.test = test
        }

        fun ifTrueMonkeyId(ifTrueMonkeyId: Int) {
            this.ifTrueMonkeyId = ifTrueMonkeyId
        }

        fun ifFalseMonkeyId(ifFalseMonkeyId: Int) {
            this.ifFalseMonkeyId = ifFalseMonkeyId
        }

        fun removeItem(item: Int) {
            this.items.remove(item)
        }
    }

    fun monkeyInTheMiddle(lines: List<String>): Int {
        val monkeys: Map<Int, Monkey> = convertInput(lines)
        val count: Map<Int, MutableList<Int>> = monkeys.keys.associateBy({ it }, { mutableListOf() })
        repeat((0..19).count()) {
            singleRound(monkeys, count)
        }

        return count.values.map { it.size }.sorted().reversed()
            .subList(0, 2)
            .reduce { acc, next -> acc * next }
    }

    private fun singleRound(monkeys: Map<Int, Monkey>, count: Map<Int, MutableList<Int>>) {
        monkeys.values.forEach { monkey ->
            monkey.items.toList().forEach { item ->
                var result: Int = monkey.operation.invoke(item)
                result /= 3
                val newMonkeyId = when (monkey.test.invoke(result)) {
                    true -> monkey.ifTrueMonkeyId
                    false -> monkey.ifFalseMonkeyId
                }
                monkey.removeItem(item)
                monkeys[newMonkeyId]!!.addItem(result)
                count[monkey.id]!!.add(item)
            }
        }
    }

    private fun convertInput(lines: List<String>): Map<Int, Monkey> {
        return lines.chunked(7)
            .map { it ->
                val monkey = Monkey()
                it.map { it.trim() }
                    .forEach { line ->
                        if (line.matches(Regex("Monkey \\d+:"))) {
                            monkey.withId(Regex("\\d+").find(line)!!.value.toInt())
                        } else if (line.matches(Regex("Starting items: .+"))) {
                            val items = line.removePrefix("Starting items:").trim()
                                .split(",")
                                .map { it.trim() }
                                .map { it.toInt() }
                            monkey.withStartingItems(items)
                        } else if (line.matches(Regex("Operation: .+"))) {
                            val operations = line.removePrefix("Operation:").trim()
                            val operation = operations.removePrefix("new = old").trim()
                                .split(" ")
                            when (operation[0]) {
                                "*" -> monkey.withOperation {
                                    if (operation[1] == "old") it * it
                                    else it * operation[1].toInt()
                                }

                                "+" -> monkey.withOperation {
                                    if (operation[1] == "old") it + it
                                    else it + operation[1].toInt()
                                }

                                else -> throw IllegalArgumentException("Operation not supported: $line")
                            }
                        } else if (line.matches(Regex("Test: .*"))) {
                            val test = line.removePrefix("Test:").trim()
                            val divisibleBy = test.removePrefix("divisible by").trim().toInt()
                            monkey.withTest { it % divisibleBy == 0 }
                        } else if (line.matches(Regex("^If .+"))) {
                            val ifCommand = line.replace("If", "")
                                .replace("throw to monkey", "")
                                .trim()
                                .split(":")
                            when (ifCommand[0]) {
                                "true" -> monkey.ifTrueMonkeyId(ifCommand[1].trim().toInt())
                                "false" -> monkey.ifFalseMonkeyId(ifCommand[1].trim().toInt())
                                else -> throw IllegalArgumentException("IF not supported: $line")
                            }
                        }
                    }
                monkey
            }
            .associateBy({ it.id }, { it })
    }
}