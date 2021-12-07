import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val horizontalPositions = input[0].split(",").map { s -> s.toInt() }.sorted()

        val neededFuels = mutableListOf<Int>()

        for (finalHorizontalPosition in horizontalPositions.first() until horizontalPositions.last() + 1 step 1) {
            neededFuels.add(horizontalPositions.sumOf { h -> abs(finalHorizontalPosition - h) })
        }

        neededFuels.sort()
        return neededFuels[0]
    }

    fun sumFromOneUntil(number: Int): Int {
        return (number + 1)*number/2
    }

    fun part2(input: List<String>): Int {
        val horizontalPositions = input[0].split(",").map { s -> s.toInt() }.sorted()

        val neededFuels = mutableListOf<Int>()

        for (finalHorizontalPosition in horizontalPositions.first() until horizontalPositions.last() + 1 step 1) {
            neededFuels.add(horizontalPositions.sumOf { h -> sumFromOneUntil(abs(finalHorizontalPosition-h)) })
        }

        neededFuels.sort()
        return neededFuels[0]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    checkEquals(part1(testInput), 37)
    println("test for part1 passed")
    val input = readInput("Day07")
    println("result for part1 >>> " + part1(input))
    checkEquals(part1(input), 328262)
    println("-----")
    checkEquals(part2(testInput), 168)
    println("test for part2 passed")
    println("result for part2 >>> " + part2(input))
    checkEquals(part2(input), 90040997)
}
