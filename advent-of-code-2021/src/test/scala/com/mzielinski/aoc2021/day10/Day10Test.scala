package com.mzielinski.aoc2021.day10

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}
import org.scalatest.prop.TableDrivenPropertyChecks._

class Day10Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "part", "result"),

    ("day10/00.txt", Part01(), 26397L),
    ("day10/01.txt", Part01(), 268845L),
    ("day10/00.txt", Part02(), 288957L),
    ("day10/01.txt", Part02(), 4038824534L)
  )

  forAll(fractions) { (filename: String, part: Part, expectedResult: Long) =>
    test(s"Day10 - Syntax Scoring - for file $filename and part $part") {
      assert(Day10.run(this.getClass.getClassLoader.getResource(filename).getPath, part) == expectedResult)
    }
  }
}