package com.mzielinski.aoc2021.day04

import com.mzielinski.aoc2021.Commons.{Part, Part01, Part02}
import org.scalatest.prop.TableDrivenPropertyChecks._

class Day04Test extends org.scalatest.funsuite.AnyFunSuite {

  private val fractions = Table(
    ("filename", "part", "result"),

    ("day04/00", Part01(), 4512),
    ("day04/01", Part01(), 87456),

    ("day04/00", Part02(), 1924),
    ("day04/01", Part02(), 15561)
  )

  forAll(fractions) { (filename: String, part: Part, expectedResult: Int) =>
    test(s"Day04 - Giant Squid - for file $filename and part $part") {
      // given
      val path1 = this.getClass.getClassLoader.getResource(s"$filename-numbers.txt").getPath
      val path2 = this.getClass.getClassLoader.getResource(s"$filename.txt").getPath

      // when:
      val result = Day04.run(path1, path2, part)

      // then:
      assert(result == expectedResult)
    }
  }
}
