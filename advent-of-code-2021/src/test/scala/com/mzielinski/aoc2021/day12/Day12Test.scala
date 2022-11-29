
package com.mzielinski.aoc2021.day12

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}
import org.scalatest.prop.TableDrivenPropertyChecks._

class Day12Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "part", "result"),

    ("day12/00.txt", Part01(), 10),
    ("day12/01.txt", Part01(), 19),
    ("day12/02.txt", Part01(), 226),
    ("day12/03.txt", Part01(), 5874),
    ("day12/00.txt", Part02(), 36),
    ("day12/01.txt", Part02(), 103),
    ("day12/02.txt", Part02(), 3509),
    ("day12/03.txt", Part02(), 153592)
  )

  forAll(fractions) { (filename: String, part: Part, expectedResult: Int) =>
    test(s"Day12 - Passage Pathing - for file $filename and part $part") {
      assert(Day12.run(this.getClass.getClassLoader.getResource(filename).getPath, part) == expectedResult)
    }
  }
}