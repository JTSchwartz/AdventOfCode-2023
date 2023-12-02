package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day
import com.jtschwartz.chorecore.replaceByMap

class Day01: Day(1) {

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 2, isTest = false).p2()

    private fun List<String>.p1(): Int = sumOf {
        with(it.split("""\D?""".toRegex()).filter(String::isNotBlank)) {
            "${first()}${last()}".toInt()
        }
    }

    private fun List<String>.p2(): Int = map {
        it.replaceByMap(
            mapOf(
                "one" to "o1e",
                "two" to "t2o",
                "three" to "t3e",
                "four" to "f4r",
                "five" to "f5e",
                "six" to "s6x",
                "seven" to "s7n",
                "eight" to "e8t",
                "nine" to "n9e",
            )
        )
    }.p1()

}
