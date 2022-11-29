package com.mzielinski.aoc2021.day06

import scala.io.Source
import scala.util.{Try, Using}

object Day06 extends App {

  case class School(lanternfishes: Map[Long, Long]) {

    private def increment(increment: Long): Option[Long] => Some[Long] =
      (counter: Option[Long]) => Some(counter.getOrElse(0L) + increment)

    def dayRecalculation(): School = {
      val recalculatedSchool: Map[Long, Long] = lanternfishes.flatMap {
        case (0, _) => None
        case (day, counter) => Some((day - 1, counter))
      }
      School(lanternfishes.get(0) match {
        case None => recalculatedSchool
        case Some(size) => recalculatedSchool
          .updatedWith(6)(increment(size))
          .updatedWith(8)(increment(size))
      })
    }

    def countLanternfishes(): Long = lanternfishes.values.sum
  }

  @annotation.tailrec
  def dayIteration(lanternfishes: School, day: Int): School = {
    if (day == 0) return lanternfishes
    dayIteration(lanternfishes.dayRecalculation(), day - 1)
  }

  def run(path: String, days: Int): Long = {
    readFile(path)
      .map(School)
      .map(school => dayIteration(school, days))
      .map(_.countLanternfishes())
      .getOrElse(-1L)
  }

  private def readFile(filename: String): Try[Map[Long, Long]] = {
    Using(Source.fromFile(filename))(_.mkString.split(",").map(_.toLong)
      .foldLeft((0L to 8L).map(_ -> 0L).toMap) {
        (acc, day) => acc.updated(day, acc(day) + 1)
      })
  }
}
