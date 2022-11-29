package com.mzielinski.aoc2021.day09

case class HeightMap(points: Array[Array[Char]]) {

  case class Point(y: Int, x: Int, value: Int) {

    def validBasin(v: Int): Boolean = v >= value && v < 9
  }

  val height: Int = points.length - 1
  val width: Int = points(0).length - 1

  def findNeighbors(y: Int, x: Int): Set[Point] = List(
    (y + 1 min height, x),
    (y - 1 max 0, x),
    (y, x + 1 min width),
    (y, x - 1 max 0)
  ).map { case (ny, nx) => Point(ny, nx, points(ny)(nx).asDigit) }.toSet

  def findBasinPoints(point: Point, acc: Set[Point]): Set[Point] = {
    val basinNeighbours = findNeighbors(point.y, point.x)
      .filter(!acc.contains(_))
      .filter(p => point.validBasin(p.value))

    if (basinNeighbours.isEmpty) acc
    else basinNeighbours.flatMap(neighbour => findBasinPoints(neighbour, acc ++ Set(neighbour)))
  }

  def findLowestPoints(): List[Point] = {
    points.zipWithIndex.flatMap {
      case (y, yIndex) => y.zipWithIndex.flatMap {
        case (x, xIndex) =>
          val neighbors = findNeighbors(yIndex, xIndex).map(_.value)
          if (!neighbors.exists { v => v < x.asDigit } && x.asDigit < 9) Some(Point(yIndex, xIndex, x.asDigit))
          else None
      }
    }.toList
  }
}
