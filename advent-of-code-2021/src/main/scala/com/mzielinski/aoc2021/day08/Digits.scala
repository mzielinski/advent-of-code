package com.mzielinski.aoc2021.day08

object Digits {

  private val digits: Map[Int, Int] = Map(
    0 -> 6,
    1 -> 2,
    2 -> 5,
    3 -> 5,
    4 -> 4,
    5 -> 5,
    6 -> 6,
    7 -> 3,
    8 -> 7,
    9 -> 6)

  def findDigit(segments: String): Option[List[Digit]] = {
    val matched: List[Digit] = digits
      .filter(entry => entry._2 == segments.length).keys
      .map(number => {
        val chars = segments.toCharArray.toList
        number match {
          case 0 => Zero(0, chars)
          case 1 => One(1, chars)
          case 2 => Two(2, chars)
          case 3 => Three(3, chars)
          case 4 => Four(4, chars)
          case 5 => Five(5, chars)
          case 6 => Six(6, chars)
          case 7 => Seven(7, chars)
          case 8 => Eight(8, chars)
          case 9 => Nine(9, chars)
        }
      }).toList
    if (matched.isEmpty) None
    else Some(matched)
  }

  sealed trait Digit {
    def segments: List[Char]

    def number(): Int

    def length(): Int = segments.length

    def isUnique(knownDigits: Map[Int, Digit]): Boolean = this match {
      case Zero(_, chars) =>
        canBe(1, chars, knownDigits) &&
          !canBe(4, chars, knownDigits) &&
          canBe(7, chars, knownDigits)
      case One(_, _) => true
      case Two(_, chars) =>
        !canBe(1, chars, knownDigits) &&
          !canBe(4, chars, knownDigits) &&
          !canBe(7, chars, knownDigits) &&
          compareWithFour(chars, knownDigits, 2)
      case Three(_, chars) =>
        canBe(1, chars, knownDigits) &&
          !canBe(4, chars, knownDigits) &&
          canBe(7, chars, knownDigits)
      case Four(_, _) => true
      case Five(_, chars) =>
        !canBe(1, chars, knownDigits) &&
          !canBe(4, chars, knownDigits) &&
          !canBe(7, chars, knownDigits) &&
          compareWithFour(chars, knownDigits, 3)
      case Six(_, chars) =>
        !canBe(1, chars, knownDigits) &&
          !canBe(4, chars, knownDigits) &&
          !canBe(7, chars, knownDigits)
      case Seven(_, _) => true
      case Eight(_, _) => true
      case Nine(_, chars) =>
        canBe(1, chars, knownDigits) &&
          canBe(4, chars, knownDigits) &&
          canBe(7, chars, knownDigits)
    }

    private def canBe(number: Int, chars: List[Char], knownDigits: Map[Int, Digit]) = {
      knownDigits(number).segments.forall(chars.contains(_))
    }

    private def compareWithFour(chars: List[Char], knownDigits: Map[Int, Digit], differences: Int): Boolean = {
      knownDigits(4).segments.count(chars.contains(_)) == differences
    }
  }

  case class Zero(number: Int, segments: List[Char]) extends Digit

  case class One(number: Int, segments: List[Char]) extends Digit

  case class Two(number: Int, segments: List[Char]) extends Digit

  case class Three(number: Int, segments: List[Char]) extends Digit

  case class Four(number: Int, segments: List[Char]) extends Digit

  case class Five(number: Int, segments: List[Char]) extends Digit

  case class Six(number: Int, segments: List[Char]) extends Digit

  case class Seven(number: Int, segments: List[Char]) extends Digit

  case class Eight(number: Int, segments: List[Char]) extends Digit

  case class Nine(number: Int, segments: List[Char]) extends Digit

}
