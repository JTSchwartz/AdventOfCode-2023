package com.jtschwartz.aoc2023.days

import com.jtschwartz.aoc2023.lib.Day

class Day07 : Day(7) {

    override fun part1() = readInput(part = 1, isTest = false).p1()
    override fun part2() = readInput(part = 1, isTest = false).p2()

    private fun List<String>.p1(): Long = parse().score()

    private fun List<String>.p2(): Long = parse {
            val jokers = remove(Game.Card.JACK)!!
            maxBy { it.value }.let { (card, count) ->
                set(card, count + jokers)
            }
    }.score()

    private fun List<String>.parse(manipulateHand: MutableMap<Game.Card, Int>.() -> Unit = {}) = map {
        val (cards, bid) = it.split("\\s+".toRegex())
        Game(cards, manipulateHand) to bid.toInt()
    }

    private fun List<Pair<Game, Int>>.score(): Long =
        sortedBy { it.first }.foldIndexed(0) { index, acc, (_, bid) -> acc + (bid * (index + 1)) }

    class Game(input: String, manipulateHand: MutableMap<Card, Int>.() -> Unit) : Comparable<Game> {
        enum class Card(private val representation: String) {
            ACE("A"),
            KING("K"),
            QUEEN("Q"),
            TEN("T"),
            NINE("9"),
            EIGHT("8"),
            SEVEN("7"),
            SIX("6"),
            FIVE("5"),
            FOUR("4"),
            THREE("3"),
            TWO("2"),
            JACK("J");

            companion object {
                operator fun get(card: String) = entries.find { it.representation == card }
            }
        }

        private val hand = input.split("").filter { it.isNotBlank() }.map { Card[it]!! }

        private val countedHand by lazy {
            Card.entries.associateWith { 0 }.toMutableMap().apply {
                hand.sortedBy { it }.forEach { card -> set(card, get(card)!! + 1) }
            }.apply(manipulateHand).toList().filter { it.second > 0 }.sortedBy { it.second }.reversed()
        }

        private val score = when (countedHand.size) {
            1 -> 6
            2 -> when (countedHand[0].second) {
                4 -> 5
                3 -> 4
                else -> throw RuntimeException()
            }

            3 -> when (countedHand[0].second) {
                3 -> 3
                2 -> 2
                else -> throw RuntimeException()
            }

            4 -> 1
            else -> 0
        }

        override fun toString() = "Game | $score | $hand | $countedHand"

        override fun compareTo(other: Game): Int {
            if (score != other.score) return score - other.score

            (0..4).forEach {
                if (hand[it] != other.hand[it]) {
                    return hand[it].compareTo(other.hand[it]) * -1
                }
            }

            return 0
        }

    }
}
