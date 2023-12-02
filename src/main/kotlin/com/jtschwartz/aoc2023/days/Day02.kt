package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day
import kotlin.math.max

class Day02 : Day(2) {

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 2, isTest = false).p2()

    private fun List<String>.p1(): Int = mapIndexed { index, game ->
        val isValidGame = game.replace("Game \\d+: ".toRegex(), "")
            .split(",", ";")
            .map(String::trim)
            .map { cubes ->
                val count = "\\d+".toRegex().find(cubes)?.value?.toInt() ?: throw IllegalArgumentException(cubes)

                count <= when {
                    cubes.contains("blue") -> 14
                    cubes.contains("green") -> 13
                    cubes.contains("red") -> 12
                    else -> throw IllegalArgumentException(cubes)
                }
            }.all { it }

        if (isValidGame) index + 1 else 0
    }.sum()

    private fun List<String>.p2(): Int = sumOf { game ->
        val cubes = game.replace("Game \\d+: ".toRegex(), "")
            .split(",", ";")
            .map(String::trim)

        var blue = 0
        var green = 0
        var red = 0

        cubes.forEach {
            val count = "\\d+".toRegex().find(it)?.value?.toInt() ?: throw IllegalArgumentException(it)
            when {
                it.contains("blue") -> blue = max(blue, count)
                it.contains("green") -> green = max(green, count)
                it.contains("red") -> red = max(red, count)
                else -> throw IllegalArgumentException(it)
            }
        }

        blue * green * red
    }

    private fun MatchGroup?.toInt(): Int = this?.value?.toInt() ?: 0
}
