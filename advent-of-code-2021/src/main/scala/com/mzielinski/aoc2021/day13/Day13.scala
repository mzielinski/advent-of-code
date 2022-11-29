package com.mzielinski.aoc2021.day13

import com.mzielinski.aoc2021.Commons
import com.mzielinski.aoc2021.Commons.{Part01, Part02}

import scala.annotation.tailrec
import scala.io.Source
import scala.util.Using

object Day13 extends App {

  case class Origami(map: Array[Array[Boolean]], instructions: List[Fold]) {

    def withInstructions(instruction: List[Fold]): Origami = Origami(map, instruction)

    def width: Int = map.head.length

    def height: Int = map.length

    def buildOrigami(): Origami = performFold()

    def countDots(): Int = map.flatten.count(dot => dot)

    @tailrec
    private def performFold(): Origami = {
      if (instructions.nonEmpty) Origami(recalculateDots(instructions.head), instructions.tail).performFold()
      else this
    }

    def performVerticalFold(vertical: Int): Array[Array[Boolean]] = {
      (0 until height).map { y =>
        (0 until vertical).map { x => map(y)(x) || map(y)(width - x - 1)
        }.toArray
      }.toArray
    }

    def performHorizontalFold(horizontal: Int): Array[Array[Boolean]] = {
      val array = (0 until horizontal).map { y =>
        (0 until width).map { x => map(y)(x) || map(height - y - 1)(x) }.toArray
      }.toArray
      array
    }

    private def recalculateDots(instruction: Fold): Array[Array[Boolean]] = {
      instruction match {
        case Horizontal(y) => performHorizontalFold(y)
        case Vertical(x) => performVerticalFold(x)
      }
    }

    override def toString: String = map.map {
      row =>
        row.foldLeft("")((acc: String, value: Boolean) => {
          acc + (if (value) "#" else " ")
        })
    }.foldLeft("")((acc: String, row: String) => acc + row + "\n")
  }

  sealed trait Fold

  case class Horizontal(y: Int) extends Fold

  case class Vertical(x: Int) extends Fold

  def run(path: String, part: Commons.Part): Int = {
    val input = readFile(path)
    part match {
      case Part01() => input.withInstructions(input.instructions.take(1)).buildOrigami().countDots()
      case Part02() => val origami = input.buildOrigami()
        println(origami)
        origami.countDots()
    }
  }

  def readFile(path: String): Origami = {

    def readDots = {
      Using(Source.fromFile(path))(_.getLines().flatMap {
        case s"$x,$y" => Some((x.toInt, y.toInt))
        case _ => None
      }.toList).getOrElse(List())
    }


    def readInstructions = {
      Using(Source.fromFile(path))(_.getLines().flatMap {
        case s"fold along y=$y" => Some(Horizontal(y.toInt))
        case s"fold along x=$x" => Some(Vertical(x.toInt))
        case _ => None
      }.toList).getOrElse(List())
    }

    val dots: List[(Int, Int)] = readDots
    val horizontalMax: Int = dots.map { case (_, y) => y }.max + 1
    val verticalMax: Int = dots.map { case (x, _) => x }.max + 1
    val map: Array[Array[Boolean]] = Array.ofDim[Boolean](horizontalMax, verticalMax)
    dots.foreach { case (x: Int, y: Int) => map(y)(x) = true }

    Origami(map, readInstructions)
  }
}
