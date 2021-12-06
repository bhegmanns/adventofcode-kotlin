fun main() {

    data class TimerValueWithCountOfLaternfishes(var timerValue: Int, var countOfLaternfishes: Long)

    class Laternfish(var internalTimer: Int){
        fun nextDay(): Boolean {
            internalTimer--
            if (internalTimer < 0) {
                internalTimer = 6
                return true
            } else {
                return false
            }
        }
    }




    fun part1(input: List<String>): Int {
        val split = input[0].split(",").map { s -> s.toInt() }

        val allLaternFishes: MutableList<Laternfish> = mutableListOf()
        for (timerdef in split) {
            allLaternFishes.add(Laternfish(timerdef))
        }

        for (i in 0 until 80 step 1) {
            var countNewLaterfishesForTheDay = 0
            for (laternfish in allLaternFishes) {
                if (laternfish.nextDay()) {
                    countNewLaterfishesForTheDay++
                }
            }
            for (c in 0 until countNewLaterfishesForTheDay step 1) {
                allLaternFishes.add(Laternfish(8))
            }

        }

        return allLaternFishes.size
    }

    fun part2(input: List<String>): Long {
        val split = input[0].split(",").map { s -> s.toInt() }



        val timers: MutableList<TimerValueWithCountOfLaternfishes> = mutableListOf()
        // {0 -> -1 ; 1 -> 0 ; 2 -> 1 ; 3 -> 2 ; 4 -> 3 ; 5 -> 4 ; 6 -> 5 ; 7 -> 6 ; 8 -> 7 ; 9 -> 8}
        for (timerValue in -1 until 9 step 1) {
            timers.add(TimerValueWithCountOfLaternfishes(timerValue, 0))
        }
        for (i in split) {
            timers[i+1].countOfLaternfishes++
        }

        for (day in 0 until 256 step 1) {
//            println(timers)
            val currentCountForNegativ = timers[1].countOfLaternfishes
            for (timerIndex in 0 until 9 step 1) {
                timers[timerIndex].countOfLaternfishes = timers[timerIndex+1].countOfLaternfishes
            }
            timers[9].countOfLaternfishes = currentCountForNegativ
            timers[7].countOfLaternfishes += currentCountForNegativ
        }

        return timers.filter{t->t.timerValue!=-1}.map { t -> t.countOfLaternfishes }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    pruefe(part1(testInput), 5934)
    println("test for part1 passed")
    val input = readInput("Day06")
    println("result for part1 >>> " + part1(input))
    println("-----")
    pruefe(part2(testInput), 26984457539L)
    println("test for part2 passed")
    println("result for part2 >>> " + part2(input))
}
