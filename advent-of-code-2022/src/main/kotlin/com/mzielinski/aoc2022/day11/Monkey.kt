package com.mzielinski.aoc2022.day11

import java.math.BigInteger

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