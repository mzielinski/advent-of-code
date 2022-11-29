package com.mzielinski.aoc2021.day04

case class Winner(board: Board, lastNumber: Integer) {

  def calculateResult(): Int = {
    board.rows
      .flatMap(row => row.elements.filter(number => !board.matchedNumbers.contains(number)))
      .foldLeft(0)(_ + _) * lastNumber
  }

  override def equals(obj: Any): Boolean = obj match {
    case winner: Winner => board.id == winner.board.id
    case _ => super.equals(obj)
  }

  override def hashCode(): Int = board.id.hashCode()
}
