package com.mzielinski.aoc2021.day05

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}
import org.scalatest.prop.TableDrivenPropertyChecks._

class Day05Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "part", "result"),

    ("day05/00.txt", Part01(), 5),
    ("day05/01.txt", Part01(), 6225),

    ("day05/00.txt", Part02(), 12),
    ("day05/01.txt", Part02(), 22116)
  )

  forAll(fractions) { (filename: String, part: Part, expectedResult: Int) =>
    test(s"Day05 - Hydrothermal Venture - for file $filename and part $part") {
      // given
      val path = this.getClass.getClassLoader.getResource(filename).getPath

      // when:
      val result = Day05.run(path, part)

      // then:
      assert(result == expectedResult)
    }
  }
}
