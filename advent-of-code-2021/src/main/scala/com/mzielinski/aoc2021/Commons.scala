package com.mzielinski.aoc2021

object Commons {

  sealed trait Part

  case class Part01() extends Part {
    override def toString: String = "01"
  }

  case class Part02() extends Part {
    override def toString: String = "02"
  }

  def binaryToInt(binaryString: String): Int = {
    Integer.parseInt(binaryString, 2)
  }

  def split[T](iter: Iterator[T])(breakOn: T => Boolean): Iterator[Iterator[T]] =
    new Iterator[Iterator[T]] {
      def hasNext: Boolean = iter.hasNext

      def next: Iterator[T] = {
        val cur = iter.takeWhile(!breakOn(_))
        iter.dropWhile(breakOn)
        cur
      }
    }.withFilter(_.nonEmpty)
}
