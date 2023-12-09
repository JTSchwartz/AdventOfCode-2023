package com.jtschwartz.aoc2023

import com.jtschwartz.aoc2023.days.*
import com.jtschwartz.aoc2023.lib.Day

fun main() {
    listOf(
        Day01(),
        Day02(),
        Day03(),
        Day04(),
        Day05(),
        Day06(),
        Day07(),
        Day08()
    ).forEach(Day::printResults)
}
