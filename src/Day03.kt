fun main() {

    fun calculateMostCommonBit(input: String): Int {
        val count0 = input.filter { it -> it == '0' }.length
        val count1 = input.filter { it -> it == '1' }.length
        if (count0 > count1) {
            return 0
        } else {
            return 1
        }
    }

    fun calculateLeastCommonBit(input: String): Int {
        val count0 = input.filter { it -> it == '0' }.length
        val count1 = input.filter { it -> it == '1' }.length
        if (count0 <= count1) {
            return 0
        } else {
            return 1
        }
    }

    fun createStringFromColumn(input: List<String>, column: Int): String {
        var result = ""
        val iterator = input.iterator()
        while (iterator.hasNext()) {
            result += iterator.next()[column]
        }
        return result
    }


    fun part1(input: List<String>): Int {

        var gammaRateString = ""
        var epsilonRateString = ""



        val newList: MutableList<String> = mutableListOf()
        for (i in input.get(0).length-1 downTo 0 step 1){
            newList.add("")
        }
        val completeIterator = input.iterator()
        while (completeIterator.hasNext()) {
            val codeString = completeIterator.next()
            for (i in input.get(0).length-1 downTo 0 step 1){
                newList[i] = newList.get(i) + codeString[i]
            }
        }
        var index = 0
        while (true){
            epsilonRateString += calculateLeastCommonBit(newList.get(index))
            gammaRateString += calculateMostCommonBit(newList.get(index++))
            if (index >= newList.size) {
                break
            }
        }
        val epsilonRate = epsilonRateString.toInt(2)
        val gammaRate = gammaRateString.toInt(2)
        return epsilonRate * gammaRate
    }

    fun part2(input: List<String>): Int {
        var currentColumn = 0

        var currentList: MutableList<String> = mutableListOf()
        currentList.addAll(input)
        while (currentList.size > 1) {
            val stringFromColumn = createStringFromColumn(currentList, currentColumn)
            val mostCommonBit = calculateMostCommonBit(stringFromColumn)
            currentList = currentList.filter { s -> "" + s[currentColumn] ==  "" + mostCommonBit }.toMutableList()
            currentColumn++
        }
        val oxygenGeneratorRate = currentList.get(0).toInt(2)

        currentList.clear()
        currentList.addAll(input)
        currentColumn = 0
        while (currentList.size > 1) {
            val stringFromColumn = createStringFromColumn(currentList, currentColumn)
            val leastCommonBit = calculateLeastCommonBit(stringFromColumn)
            currentList = currentList.filter { s -> "" + s[currentColumn] == "" + leastCommonBit }.toMutableList()
            currentColumn++
        }
        val coRate = currentList.get(0).toInt(2)

        return coRate * oxygenGeneratorRate
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    pruefe(part1(testInput), 198)

    val input = readInput("Day03")
    println(part1(input)) // wrong: 1250395
    pruefe(part2(testInput), 230)
    println(part2(input))
}
