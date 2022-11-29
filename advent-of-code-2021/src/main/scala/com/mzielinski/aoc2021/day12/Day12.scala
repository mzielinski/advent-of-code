package com.mzielinski.aoc2021.day12

import com.mzielinski.aoc2021.Commons
import com.mzielinski.aoc2021.Commons.{Part01, Part02}

import scala.io.Source
import scala.util.{Try, Using}

object Day12 extends App {

  def run(path: String, part: Commons.Part): Int = {
    readFile(path)
      .map(PathSearcher)
      .map(_.findPaths(partStrategy(part), "start", List()))
      .map(_.size)
      .get
  }

  private def partStrategy(part: Commons.Part): Int = {
    part match {
      case Part01() => 1
      case Part02() => 2
    }
  }

  private def readFile(path: String): Try[List[List[String]]] = {
    Using(Source.fromFile(path))(_.getLines().map { case s"$from-$to" => from :: to :: Nil }.toList)
  }
}
