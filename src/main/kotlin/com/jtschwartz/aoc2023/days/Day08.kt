package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day
import kotlin.math.max

class Day08: Day(8) {

    private lateinit var directions: List<Int>
    private lateinit var nodes: Map<String, List<String>>

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 2, isTest = false).p2()

    private fun List<String>.p1(): Int = parse().navigate("AAA") { it == "ZZZ" }
    private fun List<String>.p2(): Long = parse().run {
        nodes.keys.filter { it[2] == 'A' }.map { start -> navigate(start) { node -> node[2] == 'Z' }.toLong() }.lcm()
    }

    private fun List<String>.parse() {
        val breakdown = { input: String -> """(\w+) = \((\w+), (\w+)\)""".toRegex().matchEntire(input)?.destructured
            ?: throw IllegalArgumentException(input) }

        directions = get(0).toCharArray().map { if (it == 'L') 0 else 1 }
        nodes = drop(2).associate {
            val (node, left, right) = breakdown(it)

            node to listOf(left, right)
        }
    }

    private tailrec fun Any.navigate(node: String, step: Int = 0, successCase: (String) -> Boolean): Int {
        if (successCase(node)) return step

        val side = directions[step % directions.size]
        return navigate(nodes[node]!![side], step + 1, successCase)
    }

    private infix fun Long.lcm(other: Long): Long {
        val larger = max(this, other)
        val maxLcm = this * other
        var lcm = larger
        while (lcm <= maxLcm) {
            if (listOf(this, other).all { lcm % it == 0L }) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun List<Long>.lcm(): Long {
        var result = get(0)
        for (i in 1 ..< size) {
            result = result lcm get(i)
        }
        return result
    }
}
