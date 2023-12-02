package com.jtschwartz.aoc2023.lib

abstract class Day(val day: Int) {

    abstract fun part1(): Any

    abstract fun part2(): Any

    fun readInput(part: Int, isTest: Boolean) = readInput(day, part, isTest)

    fun printResults() = results(day, part1(), part2())
}
