package aoc2020

import pruefe
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val listIterator = input.iterator()
        var currentIndex = 0
        var result = 0
        while (listIterator.hasNext()) {
            val current = listIterator.next()

            val sublistIterator = input.subList(currentIndex + 1, input.size).iterator()
            for (s in sublistIterator) {
                if (s.toInt() + current.toInt() == 2020){
                    result = s.toInt() * current.toInt()
                    break
                }
            }
            currentIndex++
            if (result != 0) {
                break
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val listIterator = input.iterator()
        var currentIndex = 0
        var result = 0
        while (listIterator.hasNext()) {
            val current = listIterator.next()

            val sublistIterator = input.subList(currentIndex + 1, input.size).iterator()
            for (s in sublistIterator) {
                if (s.toInt() + current.toInt() == 2020){
                    result = s.toInt() * current.toInt()
                    break
                }
            }
            currentIndex++
            if (result != 0) {
                break
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("aoc2020/Day2020_01_test")
    pruefe(part1(testInput), 514579)

    val input = readInput("aoc2020/Day2020_01")
    println(part1(input))

    pruefe(part2(testInput), 241861950)
    println(part2(input))
}
