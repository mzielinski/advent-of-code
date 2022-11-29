package com.mzielinski.aoc2021.day08

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}
import org.scalatest.prop.TableDrivenPropertyChecks._

class Day08Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "part", "result"),

    ("day08/00.txt", Part01(), 26),
    ("day08/01.txt", Part01(), 548),

    ("day08/00.txt", Part02(), 61229),
    ("day08/01.txt", Part02(), 1074888)
  )

  forAll(fractions) { (filename: String, part: Part, expectedResult: Int) =>
    test(s"Day08 - Seven Segment Search - for file $filename and part $part") {
      assert(Day08.run(this.getClass.getClassLoader.getResource(filename).getPath, part) == expectedResult)
    }
  }
}