package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day
import com.jtschwartz.aoc2023.lib.countingMap
import com.jtschwartz.aoc2023.lib.headTail
import com.jtschwartz.aoc2023.lib.toInts
import com.jtschwartz.chorecore.div

class Day04: Day(4) {

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 1, isTest = false).p2()

    private fun List<String>.p1(): Int = sumOf { card ->
        val (winners, numbers) = card.processCard()

        score(winners, numbers) { it * 2 }
    }

    private fun List<String>.p2(): Int {
        val counter = countingMap(size) { 1 }

        forEachIndexed { index, card ->
            val (winners, numbers) = card.processCard()
            score(winners, numbers) { it + 1 }.let {  points ->
                (1..points).map { it + index }.forEach { counter[it] = counter[it]!! + 1 * counter[index]!! }
            }
        }

        return counter.values.sum()
    }

    private fun String.processCard(): List<List<Int>> = replace("Card\\s+\\d+: ".toRegex(), "")
        .split("|").map { (it / ' ').toInts() }

    private fun score(winners: List<Int>, numbers: List<Int>, points: Int = 0, transform: (Int) -> Int): Int {
        if (numbers.isEmpty()) return points

        val (head, tail) = numbers.headTail()
        return if (winners.contains(head)) {
            score(winners, tail, if (points == 0) 1 else transform(points), transform)
        } else score(winners, tail, points, transform)
    }

}
