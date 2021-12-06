import java.lang.Integer.signum

fun main() {

    data class Direction(val deltaX: Int, val deltaY: Int)

    data class Point(val x: Int, val y: Int){

        fun getDirectionTo(other: Point): Direction {
            return Direction(getXDirectionTo(other), getYDirectionTo(other))
        }

        fun getXDirectionTo(other: Point): Int {
            return signum(other.x - x)
        }

        fun getYDirectionTo(other: Point): Int {
            return signum(other.y - y)
        }

        fun toNextPointInDirection(direction: Direction): Point {
            return Point(x + direction.deltaX, y + direction.deltaY)
        }

        fun backToPointFromDirection(direction: Direction): Point {
            return Point(x - direction.deltaX, y - direction.deltaY)
        }

        fun haveSameXCoordinate(other: Point): Boolean {
            return x==other.x
        }

        fun haveSameYCoordinate(other: Point): Boolean {
            return y==other.y
        }
    }

    class Line//example input-line: "0,9 -> 5,9"
        (lineDefinition: String) {
        private val from: Point
        private val to: Point

        fun getOverlappedPoints(): List<Point>{
            val points: MutableList<Point> = mutableListOf()

            if (isVertical() || isHorizontal() || isDiagonal()) {
                val direction = from.getDirectionTo(to)

                var currentPoint = from.backToPointFromDirection(direction)
                do {
                    currentPoint = currentPoint.toNextPointInDirection(direction)
                    points.add(currentPoint)
                }while(currentPoint != to)
            }

            return points
        }

        fun isVertical(): Boolean {
            return from.haveSameXCoordinate(to)
        }

        fun isHorizontal(): Boolean {
            return from.haveSameYCoordinate(to)
        }

        fun isDiagonal(): Boolean {
            return kotlin.math.abs(from.x - to.x) == kotlin.math.abs(from.y - to.y)
        }

        init {
            val split = lineDefinition.split("->")
            val fromString = split[0].trim().split(",")
            val toString = split[1].trim().split(",")
            val fromX = fromString[0].toInt()
            val fromY = fromString[1].toInt()
            val toX = toString[0].toInt()
            val toY = toString[1].toInt()
            from = Point(fromX, fromY)
            to = Point(toX, toY)
        }
    }

    fun getCountOfAtLeastTimesOverlappedPoints(lines: List<Line>, minimumOverlapCount: Int): Int {
        val overlappedPoints = lines.map { l -> l.getOverlappedPoints() }.flatten()
        val countOverlapped: MutableMap<Point, Int> = mutableMapOf()

        for (overmappedPoint in overlappedPoints) {
            if (!countOverlapped.containsKey(overmappedPoint)) {
                countOverlapped.put(overmappedPoint, 0)
            }
            val currentCount = countOverlapped.get(overmappedPoint)
            if (currentCount != null) {
                countOverlapped.put(overmappedPoint, currentCount + 1)
            }
        }

        return countOverlapped.filter { (_, c) -> c >= minimumOverlapCount }.count()
    }



    fun part1(input: List<String>): Int {
        val relevantLines = input.map { i -> Line(i) }.filter { l -> l.isVertical() || l.isHorizontal() }

        return getCountOfAtLeastTimesOverlappedPoints(relevantLines, 2)
    }

    fun part2(input: List<String>): Int {
        val relevantLines = input.map { i -> Line(i) }.filter { l -> l.isVertical() || l.isHorizontal() || l.isDiagonal() }
        return getCountOfAtLeastTimesOverlappedPoints(relevantLines, 2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    checkEquals(part1(testInput), 5)

    val input = readInput("Day05")
    println(part1(input))
    checkEquals(part2(testInput), 12)
    println(part2(input))
}
