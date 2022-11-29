package com.mzielinski.aoc2021.day01

import scala.io.Source

object Day01 extends App {

  def readFile(filename: String): Array[Int] = {
    val file = Source.fromFile(filename)
    file.getLines
      .toArray
      .map(Integer.valueOf(_))
  }

  def compare(current: Int, previous: Int): Int = {
    if (previous < current) 1 else 0
  }

  def countIncreases(n: Array[Int], f: Array[Int] => Option[Int]) = {

    @annotation.tailrec
    def go(previous: Int, counter: Int, rem: Array[Int]): Int = {
      if (rem.isEmpty) counter
      else {
        f(rem) match {
          case Some(current) => go(current, counter + compare(current, previous), rem.tail)
          case None => counter
        }
      }
    }

    go(f(n).head, 0, n.tail)
  }

  def day01Part01: Array[Int] => Option[Int] = {
    xs: Array[Int] => Some(xs.head)
  }

  def day01Part02: Array[Int] => Option[Int] = {
    xs: Array[Int] => {
      if (xs.length > 2) Some(xs.take(3).sum)
      else None
    }
  }

  def run(filename: String, part: String): Int = {
    val ints = readFile(filename)

    countIncreases(ints, part match {
      case "01" => day01Part01
      case "02" => day01Part02
      case _ => throw new UnsupportedOperationException
    })
  }
}
