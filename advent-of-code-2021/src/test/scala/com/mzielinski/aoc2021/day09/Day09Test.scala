package com.mzielinski.aoc2021.day09

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}
import org.scalatest.prop.TableDrivenPropertyChecks._

class Day09Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "part", "result"),

    ("day09/00.txt", Part01(), 15),
    ("day09/01.txt", Part01(), 436),
    ("day09/00.txt", Part02(), 1134),
    ("day09/01.txt", Part02(), 1317792)
  )

  forAll(fractions) { (filename: String, part: Part, expectedResult: Int) =>
    test(s"Day09 - Smoke Basin - for file $filename and part $part") {
      assert(Day09.run(this.getClass.getClassLoader.getResource(filename).getPath, part) == expectedResult)
    }
  }
}