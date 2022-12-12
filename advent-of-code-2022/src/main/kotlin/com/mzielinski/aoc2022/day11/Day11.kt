package com.mzielinski.aoc2022.day11

import java.math.BigInteger

class Day11 {

    fun monkeyInTheMiddle(lines: List<String>, rounds: Int): Long {
        val monkeys: Map<Int, Monkey> = Day11InputReader.convertInput(lines)
        val count: Map<Int, MutableList<BigInteger>> = monkeys.keys.associateBy({ it }, { mutableListOf() })
        repeat((1..rounds).count()) {
            singleRound(monkeys, count)
        }

        return count.values.map { it.size.toLong() }.sorted().reversed()
            .subList(0, 2)
            .reduce { acc, next -> acc * next }
    }

    private fun singleRound(monkeys: Map<Int, Monkey>, count: Map<Int, MutableList<BigInteger>>) {
        monkeys.values.forEach { monkey ->
            monkey.items.toList().forEach { item ->
                val result: BigInteger = monkey.operation.invoke(item).div(BigInteger.valueOf(3))
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

    fun monkeyInTheMiddle02(input: List<String>, rounds: Int): Long {
        val monkeys = input.chunked(7)
        val inspectionCounts = MutableList(monkeys.size) { 0L }
        val items = monkeys.map { lines ->
            lines[1].substringAfter(": ").split(", ").map { it.toLong() }.toMutableList()
        }
        val mod = monkeys.map { it[3].split(" ").last().toInt() }.reduce(Int::times)
        repeat(rounds) {
            monkeys.forEachIndexed { m, lines ->
                items[m].forEach { old ->
                    inspectionCounts[m]++
                    val (d, m1, m2) = (3..5).map { lines[it].split(" ").last().toInt() }
                    val x = lines[2].split(" ").last().toLongOrNull() ?: old
                    var worry = if ("+" in lines[2]) old + x else old * x
                    worry %= mod
                    items[if (worry % d == 0L) m1 else m2] += worry
                }
                items[m].clear()
            }
        }
        return inspectionCounts.sortedDescending().let { it[0] * it[1] }
    }
}