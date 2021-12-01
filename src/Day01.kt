fun main() {
    fun part1(input: List<String>): Int {
        var preValue: Int = Int.MAX_VALUE
        var currentValue: Int
        var countIncreasedValues = 0

        for (s in input) {
            currentValue = s.toInt()
            if (currentValue > preValue) {
                countIncreasedValues++
            }
            preValue = currentValue
        }
        return countIncreasedValues
    }

    fun part2(input: List<String>): Int {

        val newList:MutableList<String> = mutableListOf()

        for (currentIndex in IntRange(0, input.size - 3)) {
            val subList = input.subList(currentIndex, currentIndex + 3)
            val sum = subList.stream().mapToInt(String::toInt).sum()
            newList.add("" + sum)
        }

        return part1(newList)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    pruefe(part1(testInput), 7)
    pruefe(part2(testInput), 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
