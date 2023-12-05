package com.jtschwartz.aoc2023.lib

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: Int, part: Int, isTest: Boolean) = Path("src/main/resources/day$day/${if (isTest) "test$part" else "input"}.txt").readLines()

fun List<String>.toInts(): List<Int> = filter { it.isNotBlank() }.map { it.trim().toInt() }

fun <T> List<T>.head(): T = first()

fun <T> List<T>.tail(): List<T> = drop(1)

fun <T> List<T>.headTail(): Pair<T,List<T>> = head() to tail()

fun countingMap(size: Int, default: Int = 0): MutableMap<Int,Int> = (0..<size).associateWith { default }.toMutableMap()

fun results(day: Int, part1: Any, part2: Any) {
    println("=".repeat(20))
    println()
    println("Day $day")
    println("Part 1: $part1")
    println("Part 2: $part2")
    println()
}
