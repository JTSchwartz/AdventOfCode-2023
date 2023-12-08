package com.jtschwartz.aoc2023.lib

abstract class Day(val day: Int) {

    abstract fun part1(): Any

    abstract fun part2(): Any

    fun readInput(part: Int, isTest: Boolean) = readInput(day, part, isTest)

    fun printResults() {

        val p1 = part1()
        val p2 = part2()
        println("=".repeat(20))
        println()
        println("Day $day")
        println("Part 1: $p1")
        println("Part 2: $p2")
        println()
    }
}
