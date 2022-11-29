
package com.mzielinski.aoc2021.day13

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}
import org.scalatest.prop.TableDrivenPropertyChecks._

class Day13Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "part", "result"),

    ("day13/00.txt", Part01(), 17),
    ("day13/01.txt", Part01(), 689),
    ("day13/00.txt", Part02(), 16),
    ("day13/01.txt", Part02(), 91),
  )

  forAll(fractions) { (filename: String, part: Part, expectedResult: Int) =>
    test(s"Day13 - Transparent Origami - for file $filename and part $part") {
      assert(Day13.run(this.getClass.getClassLoader.getResource(filename).getPath, part) == expectedResult)
    }
  }
}