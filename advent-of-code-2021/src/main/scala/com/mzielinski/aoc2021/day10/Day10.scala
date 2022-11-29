package com.mzielinski.aoc2021.day10

import com.mzielinski.aoc2021.Commons
import com.mzielinski.aoc2021.Commons.{Part01, Part02}

import scala.annotation.tailrec
import scala.io.Source
import scala.util.{Try, Using}

object Day10 extends App {

  def covertToOpen(char: Char): (Char, Boolean) = char match {
    case ')' => ('(', true)
    case ']' => ('[', true)
    case '}' => ('{', true)
    case '>' => ('<', true)
    case open => (open, false)
  }

  @tailrec
  def findLastOpenChars(chars: List[Char], lastOpens: List[Char]): Either[Char, List[Char]] = {
    if (chars.isEmpty) return Right(lastOpens)

    val currentChar = chars.head
    val (openChar: Char, close: Boolean) = covertToOpen(currentChar)

    if (!close) findLastOpenChars(chars.tail, openChar :: lastOpens)
    else if (close && lastOpens.head == openChar) findLastOpenChars(chars.tail, lastOpens.drop(1))
    else Left(currentChar)
  }


  def run(path: String, part: Commons.Part): Long = {
    readFile(path)
      .map {
        lines => {
          part match {
            case Part01() => part1(lines)
            case Part02() => part2(lines)
          }
        }
      }.getOrElse(-1)
  }


  def calculatePart2(openChars: List[Char]): Long = {
    openChars.foldLeft(0L)((acc: Long, char: Char) => {
      val charToValue = char match {
        case '(' => 1
        case '[' => 2
        case '{' => 3
        case '<' => 4
      }
      acc * 5 + charToValue
    })
  }

  private def part2(lines: List[List[Char]]): Long = {
    val result = lines
      .flatMap { line =>
        findLastOpenChars(line, List()) match {
          case Left(_) => None
          case Right(openChars) => Some(calculatePart2(openChars))
        }
      }
      .sorted
      .toArray
    result(result.length / 2)
  }

  private def part1(lines: List[List[Char]]): Int = {
    lines
      .flatMap { line =>
        findLastOpenChars(line, List()) match {
          case Left(invalidChar) => Some(invalidChar)
          case Right(_) => None
        }
      }
      .map {
        case ')' => 3
        case ']' => 57
        case '}' => 1197
        case '>' => 25137
      }
      .sum
  }

  private def readFile(path: String): Try[List[List[Char]]] = {
    Using(Source.fromFile(path))(_.getLines().map(_.toList).toList)
  }
}
