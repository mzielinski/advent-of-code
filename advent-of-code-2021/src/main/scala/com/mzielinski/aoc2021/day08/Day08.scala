package com.mzielinski.aoc2021.day08

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}
import com.mzielinski.aoc2021.day08.Digits.Digit

import scala.annotation.tailrec
import scala.io.Source
import scala.util.{Try, Using}

object Day08 extends App {

  case class Entry(uniqSignalPattern: List[String], number: List[String]) {
    def knownNumbers(): Map[Int, Digit] = {
      uniqSignalPattern.flatMap(Digits.findDigit)
        .filter(_.length == 1)
        .map(_.head)
        .map(d => (d.number(), d))
        .toMap
    }
  }

  def run(path: String, part: Part): Int = {
    readFile(path).map(sumEntries(_, part)).getOrElse(-1)
  }

  private def sumEntries(entries: List[Entry], part: Part) = {
    entries.foldLeft(0)(_ + sumNumbersInSingleEntry(_, part))
  }

  private def sumNumbersInSingleEntry(entry: Entry, part: Part): Int = part match {
    case Part01() => entry.number.foldLeft(0)(_ + part01(_))
    case Part02() => entry.number.foldLeft("")(_ + part02(_, entry.knownNumbers()).getOrElse("")).toInt
  }

  private def part01(number: String): Int = {
    Digits.findDigit(number).filter(_.length == 1).map(_ => 1).getOrElse(0)
  }

  private def part02(number: String, knownDigits: Map[Int, Digit]): Either[Exception, String] = {
    Digits.findDigit(number)
      .flatMap { digits =>
        if (digits.isEmpty) None
        else tryToFindUniqueNumber(digits, knownDigits)
      }
      .map(_.number())
      .map(_.toString)
      .map(Right(_))
      .getOrElse(Left(new IllegalArgumentException))
  }

  @tailrec
  def tryToFindUniqueNumber(possibleDigits: List[Digit], knownDigits: Map[Int, Digit]): Option[Digit] = {
    val digit = possibleDigits.head
    val isUniqueDigit = digit.isUnique(knownDigits)
    val possibleDigitsForNextIteration = if (!isUniqueDigit) possibleDigits.tail else possibleDigits
    val extendedKnownDigits = if (isUniqueDigit) knownDigits ++ Map(digit.number() -> digit) else knownDigits

    if (possibleDigitsForNextIteration.isEmpty || extendedKnownDigits.contains(digit.number())) Some(digit)
    else tryToFindUniqueNumber(possibleDigitsForNextIteration, extendedKnownDigits)
  }

  private def readFile(path: String): Try[List[Entry]] = {
    Using(Source.fromFile(path))(_.getLines().map {
      line =>
        val entry = line.split("\\|").toList
        Entry(convertToList(entry.headOption), convertToList(entry.lift(1)))
    }.toList)
  }

  private def convertToList(input: Option[String]) = {
    input.map(_.trim.split(" ").toList).getOrElse(List())
  }
}
