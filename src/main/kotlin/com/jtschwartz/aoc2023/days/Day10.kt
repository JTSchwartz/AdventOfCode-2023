package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day
import com.jtschwartz.chorecore.attempt
import java.lang.RuntimeException

typealias Movement = Pair<Int, Int>

class Day10 : Day(10) {

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 2, isTest = false).p2()

    private lateinit var startingPosition: Movement
    private lateinit var map: MutableList<MutableList<Legend>>

    private fun List<String>.p1(): Long = parse().apply { findStart() }.move(Direction.WEST) / 2
    private fun List<String>.p2(): Long {
        parse().apply { findStart() }.move(Direction.WEST)

        for (row in indices) {
            for (col in get(row).indices) {
                if (map.at(col, row) == Legend.INTERIOR) {
                    Direction.entries.forEach {
                        map.paint(col to row to it)
                    }
                }
            }
        }

//        map.forEach {
//            it.forEach { c ->
//                print(c.rep)
//            }
//            println()
//        }

        return 0L
    }

    private fun List<List<Legend>>.paint(position: Movement) {
        if (at(position.first, position.second) != Legend.EXTERIOR) return
        map[position.second][position.first] = Legend.INTERIOR
        Direction.entries.forEach {
            paint(position to it)
        }
    }

    private fun List<String>.parse(): List<List<Legend>> = map { it.toCharArray().map(Legend::get) }
        .apply {
            val cols = get(0).size
            val rows = size
            map = buildList {
                for (row in 0..<rows) {
                    add(" ".repeat(cols).toCharArray().map { Legend.EXTERIOR }.toMutableList())
                }
            }.toMutableList()
        }

    private fun List<List<Legend>>.at(x: Int, y: Int) = get(y)[x]

    private fun List<List<Legend>>.findStart() {
        for (row in indices) {
            for (col in get(row).indices) {
                if (at(row, col) == Legend.START) {
                    startingPosition = row to col
                    break
                }
            }
        }
    }

    private fun List<List<Legend>>.nextMove(position: Movement, cameFrom: Direction): Direction {
        val currentType = at(position.first, position.second)
        return if (currentType == Legend.START) {
            Direction.entries.find { direction ->
                (position to direction) { x, y ->
                    at(x, y).movements.containsKey(direction)
                }
            }!!
        } else {
            currentType.movements.toList().filter { (direction, _) -> direction == cameFrom }.getOrNull(0)?.second
                ?: throw NoPossibleMoves()
        }
    }

    private tailrec fun List<List<Legend>>.move(
        cameFrom: Direction,
        position: Movement = startingPosition,
        steps: Long = 0
    ): Long {
        val going = nextMove(position, cameFrom)
        val nextPosition = position to going

        at(position.first, position.second).let {
            if (listOf(Legend.VERTICAL, Legend.HORIZONTAL).any { symbol -> symbol == it }) {
                val interior = position to cameFrom.holdTheWall
                if (map.at(interior.first, interior.second) == Legend.EXTERIOR) {
                    map[interior.second][interior.first] = Legend.INTERIOR
                }
            }
            map[position.second][position.first] = it
        }

        return if (at(nextPosition.first, nextPosition.second) == Legend.START) {
            steps + 1
        } else move(going, nextPosition, steps + 1)
    }

    enum class Direction(val movement: Movement) {
        NORTH(0 to -1),
        SOUTH(0 to 1),
        EAST(1 to 0),
        WEST(-1 to 0);

        val holdTheWall
            get() = when (this) {
                NORTH -> WEST
                SOUTH -> EAST
                EAST -> NORTH
                WEST -> SOUTH
            }
    }

    enum class Legend(val rep: Char, val movements: Map<Direction, Direction>) {
        VERTICAL(
            '|',
            mapOf(
                Direction.NORTH to Direction.NORTH,
                Direction.SOUTH to Direction.SOUTH
            )
        ),
        HORIZONTAL(
            '-',
            mapOf(
                Direction.EAST to Direction.EAST,
                Direction.WEST to Direction.WEST
            )
        ),
        NE_BEND(
            'L',
            mapOf(
                Direction.SOUTH to Direction.EAST,
                Direction.WEST to Direction.NORTH
            )
        ),
        NW_BEND(
            'J',
            mapOf(
                Direction.SOUTH to Direction.WEST,
                Direction.EAST to Direction.NORTH
            )
        ),
        SW_BEND(
            '7',
            mapOf(
                Direction.NORTH to Direction.WEST,
                Direction.EAST to Direction.SOUTH
            )
        ),
        SE_BEND(
            'F',
            mapOf(
                Direction.NORTH to Direction.EAST,
                Direction.WEST to Direction.SOUTH
            )
        ),
        EXTERIOR('.', emptyMap()),
        INTERIOR('#', emptyMap()),
        START('S', emptyMap());

        companion object {
            operator fun get(rep: Char): Legend = entries.find { it.rep == rep }!!
        }
    }

    class NoPossibleMoves : RuntimeException()

    private infix fun Movement.to(direction: Direction): Movement = this to direction.movement

    private infix fun Movement.to(newMovement: Movement): Movement = newMovement.let { (x, y) ->
        first + x to second + y
    }

    operator fun <T> Movement.invoke(func: (Int, Int) -> T): T = func(first, second)
}
