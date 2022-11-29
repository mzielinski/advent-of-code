package com.mzielinski.aoc2021.day06

import org.scalatest.prop.TableDrivenPropertyChecks._

class Day06Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "result", "days"),
    ("day06/00.txt", 26L, 18),
    ("day06/00.txt", 5934L, 80),
    ("day06/01.txt", 371379L, 80),
    ("day06/00.txt", 26984457539L, 256),
    ("day06/01.txt", 1674303997472L, 256)
  )

  forAll(fractions) { (filename: String, expectedResult: Long, days: Int) =>
    test(s"Day06 - Lanternfish - for file $filename and $days days") {
      assert(Day06.run(this.getClass.getClassLoader.getResource(filename).getPath, days) == expectedResult)
    }
  }
}

