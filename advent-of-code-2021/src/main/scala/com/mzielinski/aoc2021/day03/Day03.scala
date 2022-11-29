package com.mzielinski.aoc2021.day03

import com.mzielinski.aoc2021.Commons
import com.mzielinski.aoc2021.Commons.{Part01, Part02, binaryToInt}

import scala.annotation.tailrec
import scala.io.Source
import scala.util.{Try, Using}

object Day03 extends App {

  sealed trait Bit

  case class Zero() extends Bit {
    override def toString: String = "0"
  }

  case class One() extends Bit {
    override def toString: String = "1"
  }

  private def moreOnes = (zeros: Int, ones: Int) => if (ones >= zeros) One() else Zero()

  private def lessZeros = (zeros: Int, ones: Int) => if (zeros <= ones) Zero() else One()

  private def countBits(bits: List[Bit], algo: (Int, Int) => Bit): Bit = algo(bits.count(_ == Zero()), bits.count(_ == One()))

  def run(path: String, part: Commons.Part): Int = {
    readFile(path)
      .map(_.transpose)
      .map(calculateRatings(part, _))
      .map(result => result._1 * result._2)
      .getOrElse(-1)
  }

  private def calculateRatings(part: Commons.Part, rows: List[List[Bit]]) = part match {
    case Part01() => (dayPart01(rows, moreOnes), dayPart01(rows, lessZeros))
    case Part02() => (dayPart02(rows, "", moreOnes), dayPart02(rows, "", lessZeros))
  }

  private def dayPart01(input: List[List[Bit]], function: (Int, Int) => Bit): Int = {
    val binaryString = input
      .map(countBits(_, function))
      .foldLeft("")(_ + _)
    binaryToInt(binaryString)
  }

  @tailrec
  private def dayPart02(input: List[List[Bit]], binaryString: String, algo: (Int, Int) => Bit): Int = {
    if (input.isEmpty) return binaryToInt(binaryString)
    val bit = countBits(input.head, algo)
    val filteredRows = input.transpose.filter(_.head == bit)

    if (filteredRows.isEmpty) binaryToInt(binaryString + returnTheRest(input))
    else dayPart02(filteredRows.transpose.tail, binaryString + bit, algo)
  }

  private def returnTheRest(input: List[List[Bit]]) = {
    input.flatten.foldLeft("")(_ + _)
  }

  private def readFile(filename: String): Try[List[List[Bit]]] = {
    Using(Source.fromFile(filename))(_.getLines.map {
      _.split("").map {
        case "0" => Zero()
        case "1" => One()
      }.toList
    }.toList)
  }
}
