package com.mzielinski.aoc2021.day01

import org.scalatest.prop.TableDrivenPropertyChecks._

class Day01Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "part", "result"),

    ("day01/00.txt", "01", 7),
    ("day01/01.txt", "01", 1233),

    ("day01/00.txt", "02", 5),
    ("day01/01.txt", "02", 1275)
  )

  forAll(fractions) { (filename: String, part: String, expectedResult: Int) =>
    test(s"Day01 - Sonar Sweep - for file $filename and part $part") {
      // given
      val path = this.getClass.getClassLoader.getResource(filename).getPath

      // when:
      val result = Day01.run(path, part)

      // then:
      assert(result == expectedResult)
    }
  }
}
