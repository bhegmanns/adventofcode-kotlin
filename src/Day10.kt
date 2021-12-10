import java.lang.RuntimeException

fun main() {


    fun getFirstIllegalCharacter(line: String): Char{
        val openChunks: MutableList<Char> = mutableListOf()
        var lastOpenChunk = ' '
        val corespondentChunk: MutableMap<Char, Char> = mutableMapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')

        for (c in line) {
            if ("([{<".contains("" + c)) {
                lastOpenChunk = corespondentChunk[c]!!
                openChunks.add(c)
            } else {
                if ("})]>".contains("" + c)) {
                    if (lastOpenChunk != c) {
                        return c
                    } else {
                        openChunks.removeLast()
                        if (openChunks.isEmpty()) {
                            lastOpenChunk = ' '
                        } else {
                            lastOpenChunk = corespondentChunk[openChunks.last()]!!
                        }
                    }
                }
            }

        }


        return ' ' // no corrupted chunk found
    }

    fun getTotalScore(line: String): Long{
        val openChunks: MutableList<Char> = mutableListOf()
        var lastOpenChunk = ' '
        val corespondentChunk: MutableMap<Char, Char> = mutableMapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')

        for (c in line) {
            if ("([{<".contains("" + c)) {
                lastOpenChunk = corespondentChunk[c]!!
                openChunks.add(c)
            } else {
                if ("})]>".contains("" + c)) {
                    if (lastOpenChunk != c) {
                        return 0 // corrupted lines do not hae to total Score ;)
                    } else {
                        openChunks.removeLast()
                        if (openChunks.isEmpty()) {
                            lastOpenChunk = ' '
                        } else {
                            lastOpenChunk = corespondentChunk[openChunks.last()]!!
                        }
                    }
                }
            }

        }

        var totalPoints = 0L
        for (c in openChunks.asReversed()) {
            val pointForClosing = ClosingValue.getClosingValueFromCharacter(corespondentChunk[c]!!)
            totalPoints = totalPoints * 5 + pointForClosing
        }
        return totalPoints
    }

    fun part1(input: List<String>): Int {
        return input.map { line -> getFirstIllegalCharacter(line) }.sumOf { c -> ClosingValue.getErrorCodeFromCharacter(c) }
    }

    fun part2(input: List<String>): Long {
        val totalScores = input.map { line -> getTotalScore(line) }.filter { i -> i != 0L }.toMutableList()
        if (totalScores.size % 2 == 0) {
            throw RuntimeException("no odd numbers of total scores: " + totalScores)
        }
        totalScores.sort()
        return totalScores[totalScores.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    checkEquals(part1(testInput), 26397)
    println("test for part1 passed")
    val input = readInput("Day10")
    println("result for part1 >>> " + part1(input))
    checkEquals(part1(input), 343863)
    println("-----")
    checkEquals(part2(testInput), 288957)
    println("test for part2 passed")
    println("result for part2 >>> " + part2(input))
    checkEquals(part2(input), 2924734236)
}

enum class ClosingValue(var character: Char, var errorValue: Int, var closingValue: Int){
    ONE(')', 3, 1),
    TWO(']', 57, 2),
    THREE('}', 1197, 3),
    FOUR('>', 25137, 4),
    DEFAULT(' ', 0, 0);

    companion object EV {

        private fun getEnumFromCharacter(character: Char): ClosingValue {
            return values().filter { e -> e.character==character }[0]
        }

        fun getErrorCodeFromCharacter(character: Char): Int {
            return getEnumFromCharacter(character).errorValue
        }

        fun getClosingValueFromCharacter(character: Char): Int {
            return getEnumFromCharacter(character).closingValue
        }
    }
}