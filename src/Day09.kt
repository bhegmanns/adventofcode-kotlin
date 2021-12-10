fun main() {

    data class PointInMap(var column: Int, var line: Int)

    fun createMap(input: List<String>): MutableList<MutableList<Int>> {
        val lines: MutableList<MutableList<Int>> = mutableListOf()
        for (inputLine in input) {
            val line: MutableList<Int> = mutableListOf()
            for (c in inputLine) {
                line.add(("" + c).toInt())
            }
            lines.add(line)
        }
        return lines
    }

    fun calculateSingleLowPoints(lines: MutableList<MutableList<Int>>): MutableList<PointInMap> {
        val singleLowPoints: MutableList<PointInMap> = mutableListOf()
        val countColumns = lines[0].size
        for (column in 0 until countColumns step 1) {
            for (line in 0 until lines.size step 1) {
                var countHigherLocation = 0
                val currentLocationHeight = lines[line][column]

                if (column != 0) {
                    if (lines[line][column - 1] > currentLocationHeight) {
                        countHigherLocation++
                    }
                } else {
                    countHigherLocation++
                }

                if (line != 0) {
                    if (lines[line - 1][column] > currentLocationHeight) {
                        countHigherLocation++
                    }
                } else {
                    countHigherLocation++
                }

                if (column != countColumns - 1) {
                    if (lines[line][column + 1] > currentLocationHeight) {
                        countHigherLocation++
                    }
                } else {
                    countHigherLocation++
                }

                if (line != lines.size - 1) {
                    if (lines[line + 1][column] > currentLocationHeight) {
                        countHigherLocation++
                    }
                }else {
                    countHigherLocation++
                }

                if (countHigherLocation == 4) {
                    singleLowPoints.add(PointInMap(column, line))
                }
            }
        }

        return singleLowPoints
    }


    fun part1(input: List<String>): Int {
        val lines: MutableList<MutableList<Int>> = createMap(input)

        var riskLevel = 0

        val singleLowPoints = calculateSingleLowPoints(lines)
        for (singleLowPoint in singleLowPoints) {
            riskLevel += lines[singleLowPoint.line][singleLowPoint.column] + 1
        }

        return riskLevel
    }


    fun calculateBasinSize(point: PointInMap, lines: MutableList<MutableList<Int>>): Int {
        var currentSize: Int
        val countColumns = lines[0].size
        val basins = mutableSetOf<PointInMap>()
        val currentPoint = PointInMap(point.column, point.line)
        basins.add(currentPoint)

        do {
            currentSize = basins.size
            val newBasins: MutableSet<PointInMap> = mutableSetOf()
            for (pointInBasins in basins) {
                val currentColumn = pointInBasins.column
                val currentLine = pointInBasins.line

                if (currentColumn != 0 && lines[currentLine][currentColumn - 1] != 9) {
                    newBasins.add(PointInMap(currentColumn - 1, currentLine))
                }
                if (currentLine != 0 && lines[currentLine - 1][currentColumn] != 9) {
                    newBasins.add(PointInMap(currentColumn, currentLine - 1))
                }
                if (currentLine != lines.size - 1 && lines[currentLine + 1][currentColumn] != 9) {
                    newBasins.add(PointInMap(currentColumn, currentLine + 1))
                }
                if (currentColumn != countColumns - 1 && lines[currentLine][currentColumn + 1] != 9) {
                    newBasins.add(PointInMap(currentColumn + 1, currentLine))
                }
            }
            basins.addAll(newBasins)
        }while(basins.size!=currentSize)


        return currentSize
    }



    fun part2(input: List<String>): Int {
        val lines: MutableList<MutableList<Int>> = createMap(input)


        val singleLowPoints = calculateSingleLowPoints(lines)

        // know calculate each basin ...
        val basins: MutableMap<PointInMap, Int> = mutableMapOf()
        for (point in singleLowPoints) {
            val basinSize = calculateBasinSize(point, lines)
            basins[point] = basinSize
        }
        val sortedDescending = basins.values.sortedDescending()


        return sortedDescending[0]*sortedDescending[1]*sortedDescending[2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    checkEquals(part1(testInput), 15)
    println("test for part1 passed")
    val input = readInput("Day09")
    println("result for part1 >>> " + part1(input))
    checkEquals(part1(input), 439)
    println("-----")
    checkEquals(part2(testInput), 1134)
    println("test for part2 passed")
    println("result for part2 >>> " + part2(input))
    checkEquals(part2(input), 900900)
}
