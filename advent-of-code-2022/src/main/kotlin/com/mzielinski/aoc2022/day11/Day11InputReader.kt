package com.mzielinski.aoc2022.day11

import java.math.BigInteger

object Day11InputReader {

    fun convertInput(lines: List<String>): Map<Int, Monkey> {
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