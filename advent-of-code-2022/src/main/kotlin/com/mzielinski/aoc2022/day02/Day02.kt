package com.mzielinski.aoc2022.day02

class Day02 {

    enum class Game(val value: Int) {

        ROCK(1), PAPER(2), SCISSORS(3), UNKNOWN(0);

        companion object {

            fun battle(opponent: Game, me: Game): Int {
                return if (opponent == ROCK && me == SCISSORS) 0
                else if (opponent == SCISSORS && me == PAPER) 0
                else if (opponent == PAPER && me == ROCK) 0
                else if (opponent == ROCK && me == PAPER) 6
                else if (opponent == SCISSORS && me == ROCK) 6
                else if (opponent == PAPER && me == SCISSORS) 6
                else if (opponent == me) 3
                else 0
            }

            fun convertToShape(c: String): Game {
                return when (c) {
                    "A" -> ROCK
                    "B" -> PAPER
                    "C" -> SCISSORS
                    else -> UNKNOWN
                }
            }

            fun part01Conversion(c: String): Game {
                return when (c) {
                    "X" -> ROCK
                    "Y" -> PAPER
                    "Z" -> SCISSORS
                    else -> UNKNOWN
                }
            }

            fun part02Conversion(c: String, opponent: Game): Game {
                return when (c) {
                    "X" -> when (opponent) { // win
                        ROCK -> return SCISSORS
                        SCISSORS -> return PAPER
                        PAPER -> return ROCK
                        else -> return UNKNOWN
                    }

                    "Y" -> opponent // draw
                    "Z" -> when (opponent) { // lose
                        ROCK -> return PAPER
                        SCISSORS -> return ROCK
                        PAPER -> return SCISSORS
                        else -> return UNKNOWN
                    }

                    else -> UNKNOWN
                }
            }
        }
    }

    fun rockPaperScissors(lines: List<String>, part: String): Int {
        return lines.sumOf {
            val line: List<String> = it.split(' ')
            val opponent: Game = Game.convertToShape(line[0])
            val me: Game = convertMe(line[1], part, opponent)
            Game.battle(opponent, me) + me.value
        }
    }

    private fun convertMe(c: String, part: String, opponent: Game): Game {
        if (part == "01") return Game.part01Conversion(c)
        else if (part == "02") return Game.part02Conversion(c, opponent)
        throw IllegalArgumentException("Part $part is not supported")
    }
}