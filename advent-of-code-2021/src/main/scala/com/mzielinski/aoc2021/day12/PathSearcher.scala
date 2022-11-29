package com.mzielinski.aoc2021.day12

case class PathSearcher(paths: List[List[String]]) {

  def findPaths(maxStops: Int, current: String, acc: List[String]): List[List[String]] =
    current match {
      case "end" => List(current :: acc)
      case _ => paths.filter(_.contains(current))
        .map(_.filterNot(_ == current).head)
        .filter(_ != "start")
        .filter(validPath(maxStops, current, acc, _))
        .flatMap(findPaths(maxStops, _, current :: acc))
    }

  private def validPath(maxStops: Int, current: String, acc: List[String], target: String): Boolean = {
    !target.forall(_.isLower) ||
      !acc.contains(target) ||
      !seenCount(current :: acc).exists(_ >= maxStops)
  }

  private def seenCount(list: List[String]): Iterable[Int] = list
    .filter(_.forall(_.isLower))
    .groupBy(identity)
    .values
    .map(_.size)
}
