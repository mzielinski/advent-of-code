package com.mzielinski.aoc2021.day07

import com.mzielinski.aoc2021.Commons
import com.mzielinski.aoc2021.Commons.{Part01, Part02}

import java.lang.Math.abs
import scala.io.Source
import scala.util.{Try, Using}

object Day07 extends App {

  case class CrabPosition(positions: List[Int], costsStrategy: (Int, Int) => Int) {

    val range: Range = positions.min to positions.max

    def calculateHorizontalFuelCosts(proposedHorizontalPosition: Int): Int = {
      positions.foldLeft(0)((acc: Int, position: Int) => {
        acc + costsStrategy(position, proposedHorizontalPosition)
      })
    }
  }

  def findMinimalFuelCosts(crabPosition: CrabPosition): Int = {
    crabPosition.range.map(crabPosition.calculateHorizontalFuelCosts).min
  }

  def run(path: String, part: Commons.Part): Int = {
    readFile(path)
      .map(positions => CrabPosition(positions, selectCostsStrategy(part)))
      .map(findMinimalFuelCosts)
      .getOrElse(-1)
  }

  private def selectCostsStrategy(part: Commons.Part) = {
    part match {
      case Part01() => (x1: Int, x2: Int) => abs(x1 - x2)
      case Part02() => (x1: Int, x2: Int) => (0 to abs(x1 - x2)).sum
    }
  }

  private def readFile(filename: String): Try[List[Int]] = {
    Using(Source.fromFile(filename))(_.mkString.split(",").map(_.toInt).toList)
  }
}
