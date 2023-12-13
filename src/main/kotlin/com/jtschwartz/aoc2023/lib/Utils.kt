package com.jtschwartz.aoc2023.lib

import sun.swing.plaf.synth.StyleAssociation
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: Int, part: Int, isTest: Boolean) = Path("src/main/resources/day$day/${if (isTest) "test$part" else "input"}.txt").readLines()

fun List<String>.toInts(): List<Int> = filter { it.isNotBlank() }.map { it.trim().toInt() }

fun List<String>.toLongs(): List<Long> = filter { it.isNotBlank() }.map { it.trim().toLong() }

fun <T> List<T>.head(): T = first()

fun <T> List<T>.tail(): List<T> = drop(1)

fun <T> List<T>.headTail(): Pair<T,List<T>> = head() to tail()

fun countingMap(size: Int, association: (Int) -> Int = { it }): MutableMap<Int,Int> = (0..<size).associateWith(association).toMutableMap()

fun countingMap(size: Long, association: (Long) -> Long = { it }): MutableMap<Long,Long> = (0..<size).associateWith(association).toMutableMap()

