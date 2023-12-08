package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day
import com.jtschwartz.aoc2023.lib.toInts
import com.jtschwartz.aoc2023.lib.toLongs
import kotlin.collections.zip

class Day06: Day(6) {

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 1, isTest = false).p2()

    private fun List<String>.p1(): Long = map { it.replace("\\w+:\\s+".toRegex(), "").split("\\s+".toRegex()).toLongs() }.run { get(0).zip(get(1)) }.map(::solve).reduce { a, b -> a * b }
    private fun List<String>.p2(): Long = map { it.replace("\\w+:\\s+".toRegex(), "").replace("\\s+".toRegex(), "").toLong() }.run { solve(get(0) to get(1)) }

    private fun solve(pair: Pair<Long,Long>): Long {
        val (max, record) = pair
        var count = 0L

        var wasInWinningRange = false
        for (duration in 1..<max) {
            val inWinningRange = duration * (max - duration) > record
            if (inWinningRange) {
                count++
                wasInWinningRange = true
            } else if (wasInWinningRange) {
                break
            }
        }

        return count
    }
}
