package com.mzielinski.aoc2021.day02

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}

import scala.io.Source
import scala.util.Using

object Day02 extends App {

  case class Position(horizontal: Int, depth: Int, aim: Int) {
    def changeHorizontal(unit: Int): Position = {
      Position(horizontal + unit, depth, aim)
    }

    def changeDepth(unit: Int): Position = {
      Position(horizontal, depth + unit, aim)
    }

    def changeAim(unit: Int): Position = {
      Position(horizontal, depth, aim + unit)
    }
  }

  sealed trait Direction

  case class Forward(units: Int) extends Direction

  case class Up(units: Int) extends Direction

  case class Down(units: Int) extends Direction

  def readFile(filename: String) = {
    Using(Source.fromFile(filename))(_.getLines.map {
      case s"forward $units" => Forward(Integer.valueOf(units))
      case s"up $units" => Up(Integer.valueOf(units))
      case s"down $units" => Down(Integer.valueOf(units))
      case _ => throw new UnsupportedOperationException
    }.toList)
  }

  def day02part01 = (position: Position, direction: Direction) =>
    direction match {
      case Forward(units) => position.changeHorizontal(units)
      case Up(units) => position.changeDepth(-units)
      case Down(units) => position.changeDepth(units)
    }

  def day02part02 = (position: Position, direction: Direction) =>
    direction match {
      case Forward(units) => position.changeHorizontal(units).changeDepth(position.aim * units)
      case Up(units) => position.changeAim(-units)
      case Down(units) => position.changeAim(units)
    }

  def calculateSubmarinePosition(directions: List[Direction], f: (Position, Direction) => Position): Position = {
    directions.foldLeft(Position(0, 0, 0)) { (position: Position, direction: Direction) => f(position, direction) }
  }

  def run(filename: String, part: Part): Int = {
    readFile(filename)
      .map(calculateSubmarinePosition(_, part match {
        case Part01() => day02part01
        case Part02() => day02part02
      }))
      .toOption
      .map(position => position.depth * position.horizontal)
      .getOrElse(-1)
  }
}
