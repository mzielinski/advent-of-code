package com.mzielinski.aoc2021.day04

import com.mzielinski.aoc2021.Commons
import com.mzielinski.aoc2021.day04.Day04.Row

import java.util.UUID
import scala.io.Source
import scala.util.{Try, Using}

object InputReader {

  def readBoardsAsLines(path: String): Try[List[Row]] = {
    Using(Source.fromFile(path))(
      _.getLines
        .filter(_.nonEmpty)
        .map(toRow)
        .toList)
  }

  def readBoards(path: String): Try[List[Board]] = {
    Using(Source.fromFile(path))(source => {
      Commons.split(source.getLines())(_.isEmpty)
        .map(line => Board(line.map(toRow).toList, List(), UUID.randomUUID()))
        .toList
    })
  }

  def readNumbers(path: String): Try[List[Integer]] = {
    Using(Source.fromFile(path))(_
      .bufferedReader()
      .readLine()
      .split(",")
      .map(Integer.valueOf).toList)
  }

  private def toRow(line: String): Row = {
    Row(line.split(" ")
      .filter(_.nonEmpty)
      .map(Integer.valueOf)
      .toList)
  }
}
