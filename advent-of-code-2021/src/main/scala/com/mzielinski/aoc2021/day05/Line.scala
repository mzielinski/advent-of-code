package com.mzielinski.aoc2021.day05

import java.lang.Math.abs

case class Line(x1: Int, y1: Int, x2: Int, y2: Int) {

  def isVerticalLine: Boolean = y1 == y2

  def isHorizontalLine: Boolean = x1 == x2

  def isDiagonal45DegreesLine: Boolean = isDownDiagonal || isUpDiagonal

  private def isDownDiagonal = abs(x1 - y1) == abs(x2 - y2)

  private def isUpDiagonal = abs(x1 + y1) == abs(x2 + y2)

  def coordinates(): Option[List[(Int, Int)]] = {
    if (isHorizontalLine) Some(((y1 min y2) to (y1 max y2)).map(y => (x1, y)).toList)
    else if (isVerticalLine) Some(((x1 min x2) to (x1 max x2)).map(x => (x, y1)).toList)
    else if (isDiagonal45DegreesLine) Some(generateDiagonal())
    else None
  }

  private def generateDiagonal() = {
    val cross = for {
      x <- (x1 min x2) to (x1 max x2)
      y <- (y1 min y2) to (y1 max y2)
      v = partOfDiagonalCoordinates(x, y)
    } yield v
    cross.flatten.toList
  }

  private def partOfDiagonalCoordinates(x: Int, y: Int) = {
    if (isUpDiagonal && (x + y == x1 + y1)) Some(x, y)
    else if (isDownDiagonal && (x - y == x1 - y1)) Some(x, y)
    else None
  }
}