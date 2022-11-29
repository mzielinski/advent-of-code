package com.mzielinski.aoc2021.day04

import com.mzielinski.aoc2021.day04.Day04.Row

import java.util.UUID

case class Board(rows: List[Row], matchedNumbers: List[Integer], id: UUID) {

  def addMatched(number: Integer): Board = {
    Board(rows, if (rows.flatMap(_.elements).contains(number)) number :: matchedNumbers else matchedNumbers, id)
  }

  def findWinner(): Option[Board] = {
    val winnerInRow: List[Board] = rows.flatMap { row =>
      fullyMatched(row.elements)
    }
    if (winnerInRow.isEmpty) winnerInColumn
    else Some(winnerInRow.head)
  }

  private def winnerInColumn = {
    rows.map(_.elements).transpose.flatMap(fullyMatched).headOption
  }

  private def fullyMatched(list: List[Integer]) = {
    if (list.count(matchedNumbers.contains(_)) == list.length) Some(this)
    else None
  }
}
