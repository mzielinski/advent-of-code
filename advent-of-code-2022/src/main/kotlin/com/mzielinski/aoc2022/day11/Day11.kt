package com.mzielinski.aoc2022.day11

import java.math.BigInteger

class Day11 {

    class Monkey {

        var id: Int = -1
        val items: MutableList<BigInteger> = mutableListOf()
        lateinit var operation: (BigInteger) -> BigInteger
        lateinit var test: (BigInteger) -> Boolean
        var ifTrueMonkeyId: Int = -1
        var ifFalseMonkeyId: Int = -1

        fun withId(id: Int) {
            this.id = id
        }

        fun addItem(item: BigInteger) {
            this.items.add(item)
        }

        fun removeItem(item: BigInteger) {
            this.items.remove(item)
        }

        fun withStartingItems(startingItems: List<BigInteger>) {
            this.items.addAll(startingItems)
        }

        fun withOperation(operation: (BigInteger) -> BigInteger) {
            this.operation = operation
        }

        fun withTest(test: (BigInteger) -> Boolean) {
            this.test = test
        }

        fun ifTrueMonkeyId(ifTrueMonkeyId: Int) {
            this.ifTrueMonkeyId = ifTrueMonkeyId
        }

        fun ifFalseMonkeyId(ifFalseMonkeyId: Int) {
            this.ifFalseMonkeyId = ifFalseMonkeyId
        }
    }

    fun monkeyInTheMiddle(lines: List<String>, rounds: Int, part: String): Long {
        val monkeys: Map<Int, Monkey> = convertInput(lines)
        val count: Map<Int, MutableList<BigInteger>> = monkeys.keys.associateBy({ it }, { mutableListOf() })
        repeat((1..rounds).count()) {
            singleRound(monkeys, count, part)
        }

        return count.values.map { it.size.toLong() }.sorted().reversed()
            .subList(0, 2)
            .reduce { acc, next -> acc * next }
    }

    private fun singleRound(monkeys: Map<Int, Monkey>, count: Map<Int, MutableList<BigInteger>>, part: String) {
        monkeys.values.forEach { monkey ->
            monkey.items.toList().forEach { item ->
                val result: BigInteger = part(monkey.operation, item, part)
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

    private fun part(operation: (BigInteger) -> BigInteger, item: BigInteger, part: String): BigInteger {
        val result: BigInteger = operation.invoke(item)
        return when (part) {
            "01" -> {
                result.div(BigInteger.valueOf(3))
            }

            "02" -> {
                result
            }

            else -> throw IllegalArgumentException("Part $part not supported")
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
                                .map { it.toBigInteger() }
                            monkey.withStartingItems(items)
                        } else if (line.matches(Regex("Operation: .+"))) {
                            val operations = line.removePrefix("Operation:").trim()
                            val operation = operations.removePrefix("new = old").trim()
                                .split(" ")
                            when (operation[0]) {
                                "*" -> monkey.withOperation {
                                    if (operation[1] == "old") it.multiply(it)
                                    else it.multiply(operation[1].toBigInteger())
                                }

                                "+" -> monkey.withOperation {
                                    if (operation[1] == "old") it.add(it)
                                    else it.add(operation[1].toBigInteger())
                                }

                                else -> throw IllegalArgumentException("Operation not supported: $line")
                            }
                        } else if (line.matches(Regex("Test: .*"))) {
                            val test = line.removePrefix("Test:").trim()
                            val divisibleBy = test.removePrefix("divisible by").trim().toBigInteger()
                            monkey.withTest { it.rem(divisibleBy) == BigInteger.ZERO }
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