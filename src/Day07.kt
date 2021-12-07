import kotlin.math.abs

fun main() {





    fun part1(input: List<String>): Int {
        val horizontalPositions = input[0].split(",").map { s -> s.toInt() }

        val neededFuels = mutableListOf<Int>()

        for (iteration in 0 until horizontalPositions.size step 1) {
            val finalHorizontalPosition = horizontalPositions[iteration]
            neededFuels.add(horizontalPositions.map { h -> abs(finalHorizontalPosition-h) }.sum())
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

        for (iteration in horizontalPositions.first() until horizontalPositions.last() + 1 step 1) {
            val finalHorizontalPosition = iteration
            neededFuels.add(horizontalPositions.map { h -> sumFromOneUntil(abs(finalHorizontalPosition-h)) }.sum())
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
    println("-----")
    checkEquals(part2(testInput), 168)
    println("test for part2 passed")
    println("result for part2 >>> " + part2(input))
}
