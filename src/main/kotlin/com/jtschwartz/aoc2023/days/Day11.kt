package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day
import com.jtschwartz.aoc2023.lib.headTail
import kotlin.math.abs

class Day11: Day(11) {

    private lateinit var rowExpansion: MutableList<Boolean>
    private lateinit var colExpansion: MutableList<Boolean>
    private lateinit var galaxies: MutableList<Pair<Long,Long>>

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 1, isTest = false).p2()

    private fun List<String>.p1(): Long = findExpansion().expand().findGalaxies().run {
        println(galaxies)
        calculateDistances(galaxies)
    }
    private fun List<String>.p2(): Long = findGalaxies().run {
        calculateDistances(expandGalaxies())
    }

    private fun List<String>.findExpansion(): List<String>  = apply {
        rowExpansion = " ".repeat(size).map { true }.toMutableList()
        colExpansion = " ".repeat(get(0).length).map { true }.toMutableList()

        forEachIndexed { row, str ->
            if (str.contains('#')) {
                rowExpansion[row] = false
                var index = str.indexOf('#')
                while (index > -1) {
                    colExpansion[index] = false
                    index = str.indexOf('#', index + 1)
                }
            }
        }
    }

    private fun List<String>.findGalaxies() = apply { galaxies = mutableListOf() }.onEachIndexed { row, line ->
        if (line.contains('#')) {
            var col = line.indexOf('#')
            while (col > -1) {
                galaxies.add(row.toLong() to col.toLong())
                col = line.indexOf('#', col + 1)
            }
        }
    }

    private fun List<String>.expand(): List<String> {
        val sky = map { it.toMutableList() }.toMutableList()

        var inc = 0
        rowExpansion.forEachIndexed { row, expands ->
            if (expands) sky.add(row + inc++, ".".repeat(get(0).length).toMutableList())
        }
        inc = 0
        colExpansion.forEachIndexed { col, expands ->
            if (expands) {
                sky.forEach {
                    it.add(col + inc, '.')
                }
                inc++
            }
        }

        return sky.map { it.joinToString("") }
    }

    private fun expandGalaxies(): List<Pair<Long,Long>> {
        val mapper = { index: Int, expand: Boolean -> if (expand) index else null }
        val rows = rowExpansion.mapIndexedNotNull(mapper)
        val cols = colExpansion.mapIndexedNotNull(mapper)

        return galaxies.map {
            val x = it.first + (rows.count { row -> row < it.first } * 999999)
            val y = it.second + (cols.count { col -> col < it.second } * 999999)
            x to y
        }
    }

    private fun calculateDistances(galaxyCoords: List<Pair<Long,Long>>): Long {
        if (galaxyCoords.isEmpty()) return 0L
        val (galaxy, remaining) = galaxyCoords.headTail()
        return remaining.sumOf {
            abs(galaxy.first - it.first) + abs(galaxy.second - it.second)
        } + calculateDistances(remaining)
    }
}
