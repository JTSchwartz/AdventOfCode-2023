package com.jtschwartz.aoc2023.lib

abstract class Day(val day: Int) {

    abstract fun part1(): Any

    abstract fun part2(): Any

    fun readInput(part: Int, isTest: Boolean) = readInput(day, part, isTest)

    fun printResults() {
        println("=".repeat(20))
        println()
        println("Day $day")
        print("Part 1: ")
        println(part1())
        print("Part 2: ")
        println(part2())
        println()
    }
}
