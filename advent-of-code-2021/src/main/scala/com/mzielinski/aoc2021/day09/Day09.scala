package com.mzielinski.aoc2021.day09

import com.mzielinski.aoc2021.Commons
import com.mzielinski.aoc2021.Commons.{Part01, Part02}

import scala.io.Source
import scala.util.{Try, Using}

object Day09 extends App {

  def run(path: String, part: Commons.Part): Int = {
    readFile(path)
      .map(HeightMap)
      .map(h => part match {
        case Part01() => part1(h)
        case Part02() => part2(h)
      })
      .getOrElse(-1)
  }

  private def part1(h: HeightMap): Int = {
    h.findLowestPoints()
      .map(_.value + 1)
      .sum
  }

  private def part2(h: HeightMap): Int = {
    h.findLowestPoints()
      .map(point => h.findBasinPoints(point, Set(point)))
      .map(_.size)
      .sorted
      .reverse
      .take(3)
      .product
  }

  private def readFile(path: String): Try[Array[Array[Char]]] = {
    Using(Source.fromFile(path))(_.getLines().map(_.toArray).toArray)
  }
}
