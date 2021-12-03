fun main() {



    fun part1(input: List<String>): Int {
        var currentY = 0
        var currentX = 0

        val iterator = input.iterator()
        while (iterator.hasNext()) {
            var deltaX = 0
            var deltaY = 0
            val currentCommand: String = iterator.next()
            val split = currentCommand.split(" ")
            val command = split.get(0)
            val delta = split.get(1).toInt()
            when(command){
                "forward" -> deltaX = delta
                "up" -> deltaY = -delta
                "down" -> deltaY = delta
                else -> {
                    println("POBLEM!!!" + currentCommand)
                }
            }
            currentX += deltaX
            currentY += deltaY
            if (currentY < 0){
                println("PROBLEM, currentY ist jetzt negativ ... :(" + currentCommand + "(" + currentY + ")")
            }

        }

        return currentX * currentY
    }

    fun part2(input: List<String>): Int {
        var currentY = 0
        var currentX = 0
        var aim = 0

        val iterator = input.iterator()
        while (iterator.hasNext()) {
            var deltaX = 0
            var deltaY = 0
            val currentCommand: String = iterator.next()
            val split = currentCommand.split(" ")
            val command = split.get(0)
            val  delta = split.get(1).toInt()
            when(command){
                "forward" ->
                {deltaX = delta
                    deltaY = aim * delta}
                "up" -> aim -= delta
                "down" -> aim += delta
                else -> {
                    println("POBLEM!!!" + currentCommand)
                }
            }
            currentX += deltaX
            currentY += deltaY
//            println("command '" + currentCommand + "': (" + currentX + " / " + currentY + ")")
            if (currentY < 0){
                println("PROBLEM, currentY ist jetzt negativ ... :(" + currentCommand + "(" + currentY + ")")
            }

        }

        return currentX * currentY
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    pruefe(part1(testInput), 150)

    val input = readInput("Day02")
    println(part1(input))
    pruefe(part2(testInput), 900)
    println(part2(input))
}
