fun main() {

    fun sumIncreased(input: List<Int>): Int {
        var countIncreasedValues = 0
        val preIterator = input.iterator()
        val secondIterator = input.iterator()
        secondIterator.next()

        while (secondIterator.hasNext()) {
            if (secondIterator.next() > preIterator.next()) {
                countIncreasedValues++
            }
        }
        return countIncreasedValues
    }
    fun part1(input: List<String>): Int {
        return sumIncreased(input.map { s -> s.toInt() })
    }

    fun part2(input: List<String>): Int {
        val newList:MutableList<Int> = mutableListOf()
        val integerList = input.map { s -> s.toInt() }
        val iterator = integerList.listIterator()

        while (iterator.hasNext()) {
            try{newList.add(integerList.subList(iterator.nextIndex(), iterator.nextIndex()+3).sum())}catch(e: Exception){}
            iterator.next()
        }

        return sumIncreased(newList)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    pruefe(part1(testInput), 7)
    pruefe(part2(testInput), 5)

    val input = readInput("Day01")
    pruefe(part1(input), 1184)
    pruefe(part2(input), 1158)
    println(part1(input))
    println(part2(input))
}
