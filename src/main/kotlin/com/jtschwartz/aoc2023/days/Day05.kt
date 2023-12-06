package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day
import com.jtschwartz.aoc2023.lib.toLongs
import kotlin.math.min

class Day05 : Day(5) {

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 1, isTest = true).p2()

    private fun List<String>.p1(): Long =
        process(get(0).replace("seeds: ".toRegex(), "").split("\\s+".toRegex()).toLongs().associateWith { it })

    private fun List<String>.p2(): Long = get(0).replace("seeds: ".toRegex(), "").split("\\s+".toRegex()).toLongs()
        .chunked(2) { (origin, length) ->
            (origin..<origin + length).let {
                var remaining = it.toList()
                var min = -1L
                do {
                    val current = remaining.take(10_000)
                    remaining = remaining.drop(10_000)

                    val result = process(current.associateWith { x -> x })
                    min = if (min < 0) result else min(result, min)
                } while (remaining.isNotEmpty())
                min
            }
        }.min()

    private fun List<String>.process(origin: Map<Long, Long>): Long {
        var seeds = origin
        val mappings = mutableMapOf<Long, Long>()

        drop(2).filter { it.isNotBlank() }.forEach {
            if (it.contains("map")) {
                seeds = seeds.keys.associateWith { seed -> mappings[seed] ?: seed }
            } else {
                val (dest, src, len) = it.split("\\s+".toRegex()).toLongs()

                seeds.forEach { (seed, x) ->
                    if (x in src..<src + len) {
                        mappings[seed] = dest + (x - src)
                    }
                }
            }
        }

        return mappings.values.min()
    }
}
