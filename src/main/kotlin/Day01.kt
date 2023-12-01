package code

import com.jtschwartz.chorecore.replaceByMap

fun main() {
    val result1 = readInput(day = 1, part = 1, isTest = false).part1()

    val result2 = readInput(day = 1, part = 2, isTest = false).part2()

    results(1, result1, result2)
}

fun List<String>.part1(): Int = sumOf {
    with(it.split("""\D?""".toRegex()).filter(String::isNotBlank)) {
        "${first()}${last()}".toInt()
    }
}

fun List<String>.part2(): Int = map {
    it.replaceByMap(
        mapOf(
            "one" to "o1e",
            "two" to "t2o",
            "three" to "t3e",
            "four" to "f4r",
            "five" to "f5e",
            "six" to "s6x",
            "seven" to "s7n",
            "eight" to "e8t",
            "nine" to "n9e",
        )
    )
}.part1()
