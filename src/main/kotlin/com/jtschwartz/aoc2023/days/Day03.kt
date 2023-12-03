package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day

class Day03 : Day(3) {

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 1, isTest = false).p2()

    private var nextGearSymbol = 58

    private fun List<String>.p1(): Int = mapIndexed { row, line ->
        """\d+""".toRegex().findAll(line).sumOf {
            if (boundaries(it.range, row).contains("[^.\\d]".toRegex())) it.value.toInt() else 0
        }
    }.sum()

    private fun List<String>.p2(): Int = mutableMapOf<Char, MutableList<Int>>().apply {
            this@p2.map {
                it.replace("[^*.\\d]".toRegex(), ".")
            }.map {
                it.toList().joinToString("") { c -> (if (c == '*') (nextGearSymbol++).toChar() else c).toString() }
            }.run {
                mapIndexed { row, line ->
                    """\d+""".toRegex().findAll(line).forEach {
                        "[^.\\d]".toRegex().findAll(boundaries(it.range, row)).forEach { match ->
                            compute(match.value.first()) { _, list ->
                                list?.apply {
                                    add(it.value.toInt())
                                } ?: mutableListOf(it.value.toInt())
                            }
                        }
                    }
                }
            }
        }.toList().map { it.second }.filter { it.size == 2 }.sumOf { it[0] * it[1] }

    private fun List<String>.boundaries(range: IntRange, row: Int): String = mutableListOf(row)
        .apply {
            if (row > 0) add(row - 1)
            if (row < this@boundaries.first().length - 1) add(row + 1)
        }.joinToString("") {
            get(it).substring(range.normalize(max = first().length - 1))
        }

    private fun IntRange.normalize(max: Int = 1, min: Int = 0) = maxOf(min, start - 1)..minOf(max, endInclusive + 1)
}
