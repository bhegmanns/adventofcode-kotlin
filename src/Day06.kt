

fun main() {

    data class TimerValueWithCountOfLanternfishes(var timerValue: Int, var countOfLanternfishes: Long)

    class Lanternfish(var internalTimer: Int){
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

        val allLanternFishes: MutableList<Lanternfish> = mutableListOf()
        for (timerdef in split) {
            allLanternFishes.add(Lanternfish(timerdef))
        }

        for (i in 0 until 80 step 1) {
            var countNewLanterfishesForTheDay = 0
            for (laternfish in allLanternFishes) {
                if (laternfish.nextDay()) {
                    countNewLanterfishesForTheDay++
                }
            }
            for (c in 0 until countNewLanterfishesForTheDay step 1) {
                allLanternFishes.add(Lanternfish(8))
            }

        }

        return allLanternFishes.size
    }

    fun part2(input: List<String>): Long {
        val split = input[0].split(",").map { s -> s.toInt() }



        val timers: MutableList<TimerValueWithCountOfLanternfishes> = mutableListOf()
        // {0 -> -1 ; 1 -> 0 ; 2 -> 1 ; 3 -> 2 ; 4 -> 3 ; 5 -> 4 ; 6 -> 5 ; 7 -> 6 ; 8 -> 7 ; 9 -> 8}
        for (timerValue in -1 until 9 step 1) {
            timers.add(TimerValueWithCountOfLanternfishes(timerValue, 0))
        }
        for (i in split) {
            timers[i+1].countOfLanternfishes++
        }

        for (day in 0 until 256 step 1) {
//            println(timers)
            val currentCountForNegativ = timers[1].countOfLanternfishes
            for (timerIndex in 0 until 9 step 1) {
                timers[timerIndex].countOfLanternfishes = timers[timerIndex+1].countOfLanternfishes
            }
            timers[9].countOfLanternfishes = currentCountForNegativ
            timers[7].countOfLanternfishes += currentCountForNegativ
        }

        return timers.filter{t->t.timerValue!=-1}.map { t -> t.countOfLanternfishes }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    checkEquals(part1(testInput), 5934)
    println("test for part1 passed")
    val input = readInput("Day06")
    println("result for part1 >>> " + part1(input))
    println("-----")
    checkEquals(part2(testInput), 26984457539L)
    println("test for part2 passed")
    println("result for part2 >>> " + part2(input))
}
