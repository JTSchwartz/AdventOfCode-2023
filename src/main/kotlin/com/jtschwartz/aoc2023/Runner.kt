package com.jtschwartz.aoc2023

import com.jtschwartz.aoc2023.days.Day01
import com.jtschwartz.aoc2023.days.Day02
import com.jtschwartz.aoc2023.days.Day03
import com.jtschwartz.aoc2023.lib.Day

fun main() {
    listOf(
        Day01(),
        Day02(),
        Day03()
    ).forEach(Day::printResults)
}
