package com.mzielinski.aoc2021.day07

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}
import org.scalatest.prop.TableDrivenPropertyChecks._

class Day07Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "part", "result"),

    ("day07/00.txt", Part01(), 37),
    ("day07/01.txt", Part01(), 342730),

    ("day07/00.txt", Part02(), 168),
    ("day07/01.txt", Part02(), 92335207)
  )

  forAll(fractions) { (filename: String, part: Part, expectedResult: Int) =>
    test(s"Day07 - The Treachery of Whales - for file $filename and part $part") {
      assert(Day07.run(this.getClass.getClassLoader.getResource(filename).getPath, part) == expectedResult)
    }
  }
}