package com.mzielinski.aoc2021.day05

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}

import java.lang.Integer.valueOf
import scala.io.Source
import scala.util.Using

object Day05 extends App {

  def run(path: String, part: Part): Int = {
    readFile(path)
      .map(_.filter(filterStrategy(part)))
      .map(SubmarineMap)
      .map(_.calculateSubmarineMap())
      .map(_.flatten.count(_ > 1))
      .getOrElse(-1)
  }

  private def filterStrategy(part: Part): Line => Boolean = {
    part match {
      case Part01() => line: Line => line.isVerticalLine || line.isHorizontalLine
      case Part02() => line: Line => line.isVerticalLine || line.isHorizontalLine || line.isDiagonal45DegreesLine
    }
  }

  private def readFile(filename: String) = {
    Using(Source.fromFile(filename))(_.getLines.map {
      case s"$x1,$y1 -> $x2,$y2" => Line(valueOf(x1), valueOf(y1), valueOf(x2), valueOf(y2))
      case _ => throw new UnsupportedOperationException
    }.toList)
  }
}
