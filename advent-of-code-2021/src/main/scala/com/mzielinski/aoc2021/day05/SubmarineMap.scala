package com.mzielinski.aoc2021.day05

case class SubmarineMap(lines: List[Line]) {

  def findLargestX(lines: List[Line]): Int = {
    lines.map(line => line.x1 max line.x2).max
  }

  def findLargestY(lines: List[Line]): Int = {
    lines.map(line => line.y1 max line.y2).max
  }

  def calculateSubmarineMap(): Array[Array[Int]] = {
    iterate(lines, Array.ofDim[Int](findLargestY(lines) + 1, findLargestX(lines) + 1))
  }

  def iterate(lines: List[Line], submarineMap: Array[Array[Int]]): Array[Array[Int]] = {
    if (lines.isEmpty) submarineMap
    else iterate(lines.tail, updateSubmarineMap(submarineMap, lines.head))
  }

  private def updateSubmarineMap(submarineMap: Array[Array[Int]], line: Line): Array[Array[Int]] = {
    line.coordinates().foreach(_.foreach(coordinates => submarineMap(coordinates._2)(coordinates._1) += 1))
    submarineMap
  }
}
