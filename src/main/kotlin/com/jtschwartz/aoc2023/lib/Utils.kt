package com.jtschwartz.aoc2023.lib

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: Int, part: Int, isTest: Boolean) = Path("src/main/resources/day$day/${if (isTest) "test$part" else "input"}.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun results(day: Int, part1: Any, part2: Any) {
    println("=".repeat(20))
    println()
    println("Day $day")
    println("Part 1: $part1")
    println("Part 2: $part2")
    println()
}
