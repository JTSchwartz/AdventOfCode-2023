package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day

class Day03 : Day(3) {

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 1, isTest = false).p2()


    private fun List<String>.p1(): Int = mapIndexed { row, line ->
        """\d+""".toRegex().findAll(line).sumOf {
            if (boundaries(it.range, row).contains("[^.\\d]".toRegex())) {
                it.value.toInt()
            } else 0
        }
    }.sum()

    private fun List<String>.p2(): Int {
        var nextGearSymbol = 58
        val gears = mutableMapOf<Char, MutableList<Int>>()

        val gearified = map {
            it.replace("[^*.\\d]".toRegex(), ".")
        }.map {
            var result = it
            it.forEachIndexed { index, c ->
                if (c == '*') result = result.replaceRange(index, index + 1, (nextGearSymbol++).toChar().toString())
            }

            result
        }

        gearified.mapIndexed { row, line ->
            """\d+""".toRegex().findAll(line).forEach {
                val value = it.value.toInt()
                "[^.\\d]".toRegex().findAll(gearified.boundaries(it.range, row)).forEach { match ->
                    val gear = match.value.first()
                    if (gears.containsKey(gear)) gears[gear]!!.add(value)
                    else gears[gear] = mutableListOf(value)
                }
            }
        }

        return gears.toList().map { it.second }.filter { it.size == 2 }.sumOf { it[0] * it[1] }
    }

    private fun List<String>.boundaries(range: IntRange, row: Int): String {
        val normalizedRange = range.normalize(max = first().length - 1)
        val maxRow = first().length - 1

        return mutableListOf(row)
            .apply {
                if (row > 0) add(row - 1)
                if (row < maxRow) add(row + 1)
            }
            .map { get(it) }
            .joinToString("") {
                it.substring(normalizedRange)
            }
    }

    private fun IntRange.normalize(max: Int = 1, min: Int = 0) = maxOf(min, start - 1)..minOf(max, endInclusive + 1)
}
