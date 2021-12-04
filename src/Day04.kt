fun main() {




    class Line{
        var numbers: MutableList<Int> = mutableListOf()
        private var solved: MutableList<Boolean> = mutableListOf()
        private val formatTemplates = arrayOf("%5d", "(%3d)")

        fun printOut() {
            val numberIterator = numbers.iterator()
            val solvedIterator = solved.iterator()


            while (numberIterator.hasNext()) {
                val solved = solvedIterator.next()
                val number = numberIterator.next()


                print(formatTemplates.get(when(solved){
                    true -> 1
                    false -> 0
                }).format(number))
            }
            println()
        }

        fun setLine(lineElements: List<Int>){
            val iterator = lineElements.iterator()
            while (iterator.hasNext()) {
                val currentNumber = iterator.next()
                numbers.add(currentNumber)
                solved.add(false)
            }
        }

        fun markAsSolved(number: Int){
            val numberIterator = numbers.iterator()
            var currentIndex = 0

            while (numberIterator.hasNext()) {
                val currentNumber = numberIterator.next()
                if (currentNumber == number) {
                    solved[currentIndex] = true
                }
                currentIndex++
            }
        }

        fun getSumSolved(): Int{
            val solvedIterator = solved.iterator()
            val numberIterator = numbers.iterator()
            var sum = 0
            while (solvedIterator.hasNext()) {
                val next = numberIterator.next()
                if (solvedIterator.next()) {
                    sum += next
                }
            }
            return sum
        }

        fun getSumUnsolved(): Int{
            val solvedIterator = solved.iterator()
            val numberIterator = numbers.iterator()
            var sum = 0
            while (solvedIterator.hasNext()) {
                val next = numberIterator.next()
                if (!solvedIterator.next()) {
                    sum += next
                }
            }
            return sum
        }

        fun isSolvedCompleted(): Boolean{
            return solved.filter { s -> !s }.isEmpty()
        }
    }

    class Bingo{
        private var rows: MutableList<Line> = mutableListOf()
        private var columns: MutableList<Line> = mutableListOf()
        var solved: Boolean = false

        fun printOut(){
            if (solved) {
                println("SOLVED")
            } else {
                println("UNSOLVED")
            }

            val iterator = rows.iterator()
            while (iterator.hasNext()) {
                val line = iterator.next()
                line.printOut()
            }
        }

        fun addRow(row: List<Int>){
            val line = Line()
            line.setLine(row)
            rows.add(line)
        }

        fun prepareColumns(){
            val countColumns = rows.get(0).numbers.size
            var currentColumn = 0
            while (currentColumn < countColumns) {
                val line = Line()
                val numbersInColumn: MutableList<Int> = mutableListOf()
                val rowIterator = rows.iterator()
                while (rowIterator.hasNext()) {
                    numbersInColumn.add(rowIterator.next().numbers.get(currentColumn))
                }
                line.setLine(numbersInColumn)
                columns.add(line)

                currentColumn++
            }

        }

        fun solveNumber(number: Int): Boolean{
            for (row in rows){
                row.markAsSolved(number)
            }
            for (column in columns){
                column.markAsSolved(number)
            }

            solved =  rows.filter { r -> r.isSolvedCompleted() }.isNotEmpty() || columns.filter { r -> r.isSolvedCompleted() }.isNotEmpty()
            return solved
        }

        fun getSumSolved(): Int{
            var sum = 0
            val iterator = rows.iterator()
            while (iterator.hasNext()) {
                sum += iterator.next().getSumSolved()
            }
            return sum
        }
        fun getSumUnsolved(): Int{
            var sum = 0
            val iterator = rows.iterator()
            while (iterator.hasNext()) {
                sum += iterator.next().getSumUnsolved()
            }
            return sum
        }
    }



    fun createBingoCards(iterator: Iterator<String>): MutableList<Bingo>{
        val bingos: MutableList<Bingo> = mutableListOf()
        var currentBingo: Bingo? = null

        while (iterator.hasNext()) {
            val currentLine = iterator.next()
            if (currentLine.isEmpty()) {
                if (currentBingo != null) {
                    bingos.add(currentBingo)
                    currentBingo.prepareColumns()
                    currentBingo = null
                }
            } else {
                val row = currentLine.split(" ").filter { s -> s.isNotEmpty() }.map { s -> s.trim().toInt() }
                if (currentBingo == null) {
                    currentBingo = Bingo()
                }
                currentBingo.addRow(row)
            }
        }
        if (currentBingo != null) {
            bingos.add(currentBingo)
            currentBingo.prepareColumns()
        }

        return bingos
    }

    class BingoGame(input: List<String>){
         var numbers: List<Int> = listOf()
         var bingos: MutableList<Bingo> = mutableListOf()
        private val numberIterator: Iterator<Int>

        init{
            val iterator = input.iterator()
            numbers = iterator.next().split(",").map { s -> s.toInt() }
            bingos = createBingoCards(iterator)
            numberIterator = numbers.iterator()
        }

        fun solve(number: Int) {
            val bingoIterator = bingos.iterator()
            while (bingoIterator.hasNext()) {
                val currentBingo = bingoIterator.next()
                currentBingo.solveNumber(number)
            }
        }

        fun solveWithNextNumber(): Int {
            val currentNumber: Int
            if (numberIterator.hasNext()) {
                currentNumber = numberIterator.next()
                solve(currentNumber)
            }else{
                throw Exception("no available number")
            }
            return currentNumber
        }

        fun printOutBingos() {
            for (bingo in bingos) {
                bingo.printOut()
                println()
            }
        }

        fun isAtLeastOneBingoSolved(): Boolean {
            return bingos.filter { b -> b.solved }.isNotEmpty()
        }

        fun isEveryBingoSolved(): Boolean{
            return bingos.filter { b -> !b.solved }.isEmpty()
        }

        fun getSolvedBingos(): List<Bingo>{
            val bingos: MutableList<Bingo> = mutableListOf()
            for (bingo in this.bingos) {
                if (bingo.solved) {
                    bingos.add(bingo)
                }
            }
            return bingos
        }

        fun getUnsolvedBingos(): List<Bingo> {
            val bingos: MutableList<Bingo> = mutableListOf()
            for (bingo in this.bingos) {
                if (!bingo.solved) {
                    bingos.add(bingo)
                }
            }
            return bingos
        }
    }



    fun part1(input: List<String>): Int {
        val bingoGame = BingoGame(input)

        // now the game
        var currentNumber = 0
        while (!bingoGame.isAtLeastOneBingoSolved()) {
            currentNumber = bingoGame.solveWithNextNumber()
            println(">>> Number: " + currentNumber)
            bingoGame.printOutBingos()
        }

        val bingo = bingoGame.getSolvedBingos().get(0)
        bingo.printOut()
        println("sum unsolved = " + bingo.getSumUnsolved())
        println("sum solved = " + bingo.getSumSolved())
        val result = bingo.getSumUnsolved() * currentNumber
        println("Count solved bingos = " + bingoGame.getSolvedBingos().count())
        return result

    }

    fun part2(input: List<String>): Int {
        val bingoGame = BingoGame(input)
        var number = 0
        var unsolvedBingos: List<Bingo> = bingoGame.getUnsolvedBingos()
        while (!bingoGame.isEveryBingoSolved()) {
            number = bingoGame.solveWithNextNumber()
            println(">>> Number: " + number)
            bingoGame.printOutBingos()
            if (!bingoGame.isEveryBingoSolved()) {
                unsolvedBingos = bingoGame.getUnsolvedBingos()
            }
        }
        println("sum unsolved = " + unsolvedBingos[0].getSumUnsolved())
        println("sum solved = " + unsolvedBingos[0].getSumSolved())
        return unsolvedBingos[0].getSumUnsolved() * number
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    pruefe(part1(testInput), 4512)

    val input = readInput("Day04")
    println(part1(input))
    pruefe(part2(testInput), 1924)
    println(part2(input))
}
