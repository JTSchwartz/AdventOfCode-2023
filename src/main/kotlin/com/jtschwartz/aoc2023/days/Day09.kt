package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day
import com.jtschwartz.aoc2023.lib.toLongs

class Day09: Day(9) {

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 1, isTest = false).p2()

    private fun List<String>.p1(): Long = parse().sumOf { it.reversed().predict() }
    private fun List<String>.p2(): Long = parse().sumOf { it.predict() }

    private fun List<String>.parse(): List<List<Long>> = map { it.split("\\s+".toRegex()).toLongs() }

    private fun List<Long>.predict(): Long = if (all { it == 0L }) 0 else first() + steps().predict()

    private fun List<Long>.steps() = mapIndexedNotNull { index, current -> if (index == size - 1) null else current - get(index + 1) }
}
