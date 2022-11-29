package com.mzielinski.aoc2021.day03

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}
import org.scalatest.prop.TableDrivenPropertyChecks._

class Day03Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "part", "result"),

    ("day03/00.txt", Part01(), 198),
    ("day03/01.txt", Part01(), 741950),

    ("day03/00.txt", Part02(), 230),
    ("day03/01.txt", Part02(), 903810)
  )

  forAll(fractions) { (filename: String, part: Part, expectedResult: Int) =>
    test(s"Day03 - Binary Diagnostic - for file $filename and part $part") {
      // given
      val path = this.getClass.getClassLoader.getResource(filename).getPath

      // when:
      val result = Day03.run(path, part)

      // then:
      assert(result == expectedResult)
    }
  }
}