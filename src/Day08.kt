import java.lang.RuntimeException

fun main() {

    fun isUniqueNumberForSegment(number: Int): Boolean {
        return number == 2 || number == 3 || number == 4 || number == 7
    }

    fun part1(input: List<String>): Int {
        val rightSides: MutableList<String> = mutableListOf()
        for (string in input) {
            val indexOfDelimiter = string.indexOf('|')
            rightSides.add(string.substring(indexOfDelimiter+1))
        }

        var countOfUniqueSegments = 0
        for (left in rightSides) {
            countOfUniqueSegments += left.split(" ").map { s -> s.length }.count { i -> isUniqueNumberForSegment(i) }
        }

        return countOfUniqueSegments
    }



    data class TokenToSegment(var token: Char, var segmentNumber: Int)

    data class DigitToSegments(var digit: Int, var segmentNumbers: List<Int>)

    class SegmentRule(var listOfDigitCodes: MutableList<String>) {

        val digitToDigitToSegments: MutableMap<Int, DigitToSegments> = mutableMapOf()
        val tokenToSegments: MutableList<TokenToSegment> = mutableListOf()

        init {
            /*
            segment-number of a 7-segment-display and its resulted digit.
            digitToDigitToSegments maps the resulted digit und set segment-numbers
            Segment:
                 1111
                2    3
                2    3
                 4444
                5    6
                5    6
                 7777

             So, for example the digit 1 needs to set the segments 3 and 6,
             the digit 0 needs to set the segments 1, 2, 3, 5, 6, 7 ..

             */
            digitToDigitToSegments[0] = DigitToSegments(0, listOf(1, 2, 3, 5, 6, 7)) //
            digitToDigitToSegments[1] = DigitToSegments(1, listOf(3, 6)) //
            digitToDigitToSegments[2] = DigitToSegments(2, listOf(1, 3, 4, 5, 7)) //
            digitToDigitToSegments[3] = DigitToSegments(3, listOf(1, 3, 4, 6, 7)) //
            digitToDigitToSegments[4] = DigitToSegments(4, listOf(2, 3, 4, 6)) //
            digitToDigitToSegments[5] = DigitToSegments(5, listOf(1, 2, 4, 6, 7)) //
            digitToDigitToSegments[6] = DigitToSegments(6, listOf(1, 2, 4, 5, 6, 7)) //
            digitToDigitToSegments[7] = DigitToSegments(7, listOf(1, 3, 6)) //
            digitToDigitToSegments[8] = DigitToSegments(8, listOf(1, 2, 3, 4, 5, 6, 7))
            digitToDigitToSegments[9] = DigitToSegments(9, listOf(1, 2, 3, 4, 6, 7))
            matchDigitCodesToDigits()
        }

        private fun matchDigitCodesToDigits() {
            /*
            with the unique patterns, I get one defined connection for segment-1
            and three permutations with two elements for the segment-pairs (3/6), (2/4) and (5/7)

            in my solution, I check all possible permutations and if one match to the given
            codes I found the connection
            The permutation matches, if every given definition-code matches to da concrete digit.
             */
            checkListOfDigitCodes()

            val firstPermutation = listOfDigitCodes.filter { s -> s.length == 2 }[0].toMutableList()
            val eitherTop = listOfDigitCodes.filter { s -> s.length == 3 }[0].toMutableList()
            eitherTop.removeAll ( firstPermutation )

            val secondPermutation = listOfDigitCodes.filter { s -> s.length == 4 }[0].toMutableList()
            secondPermutation.removeAll(firstPermutation)

            val thirdPermutation = listOfDigitCodes.filter { s -> s.length == 7 }[0].toMutableList()
            thirdPermutation.removeAll(eitherTop)
            thirdPermutation.removeAll(firstPermutation)
            thirdPermutation.removeAll(secondPermutation)

            if (eitherTop.size != 1) {
                throw RuntimeException("top.size != 1: " + eitherTop)
            }
            if (firstPermutation.size != 2) {
                throw RuntimeException("one.size != 1: " + eitherTop)
            }
            if (secondPermutation.size != 2) {
                throw RuntimeException("second.size != 1: " + eitherTop)
            }
            if (thirdPermutation.size != 2) {
                throw RuntimeException("third.size != 1: " + eitherTop)
            }

            tokenToSegments.clear()
            for (first in 0 until 2 step 1) {
                for (second in 0 until 2 step 1) {
                    for (third in 0 until 2 step 1) {
                        tokenToSegments.add(TokenToSegment(eitherTop[0], 1))
                        if (first == 0) {
                            tokenToSegments.add(TokenToSegment(firstPermutation[0], 3))
                            tokenToSegments.add(TokenToSegment(firstPermutation[1], 6))
                        } else {
                            tokenToSegments.add(TokenToSegment(firstPermutation[0], 6))
                            tokenToSegments.add(TokenToSegment(firstPermutation[1], 3))
                        }

                        if (second == 0) {
                            tokenToSegments.add(TokenToSegment(secondPermutation[0], 2))
                            tokenToSegments.add(TokenToSegment(secondPermutation[1], 4))
                        } else {
                            tokenToSegments.add(TokenToSegment(secondPermutation[0], 4))
                            tokenToSegments.add(TokenToSegment(secondPermutation[1], 2))
                        }

                        if (third == 0) {
                            tokenToSegments.add(TokenToSegment(thirdPermutation[0], 5))
                            tokenToSegments.add(TokenToSegment(thirdPermutation[1], 7))
                        } else {
                            tokenToSegments.add(TokenToSegment(thirdPermutation[0], 7))
                            tokenToSegments.add(TokenToSegment(thirdPermutation[1], 5))
                        }

                        val allDigits = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                        for (code in listOfDigitCodes) {
                            allDigits.remove(getDigitForCode(code))
                        }
                        if (!allDigits.isEmpty()) { // not all given definition-codes matched
                            tokenToSegments.clear()
                        } else {
                            return
                        }
                    }
                }
            }

            throw RuntimeException("Uups, no proper connection found .. :(")

        }

        fun getDigitForCode(code: String): Int {
            /*
            comparing with digitToDigitToSegments

            1. step: get segments (segment-numbers) with are set with that code
            2. step: search in digitToDigitToSegments for the set segments, that is the digit
             */

            val codeAsChars = code.toMutableList()
            val segmentnummern: MutableList<Int> = mutableListOf()
            for (c in codeAsChars) {
                for (t in tokenToSegments) {
                    if (c == t.token) {
                        segmentnummern.add(t.segmentNumber)
                        continue
                    }
                }
            }
            val values = digitToDigitToSegments.values
            for (v in values) {
                if (segmentnummern.containsAll(v.segmentNumbers) && v.segmentNumbers.containsAll(segmentnummern)) {
                    return v.digit
                }
            }
            return -1
        }


        private fun checkListOfDigitCodes() {
            if (listOfDigitCodes == null || listOfDigitCodes.isEmpty()) {
                throw RuntimeException("listOfDigitCodes is null or empty: " + listOfDigitCodes)
            }
            if (listOfDigitCodes.size != 10) {
                throw RuntimeException("size of listOfDigitCodes is not 10 but " + listOfDigitCodes.size + ": " + listOfDigitCodes)
            }
        }

    }

    fun part2(input: List<String>): Int {
        val leftSides: MutableList<String> = mutableListOf()
        val rightSides: MutableList<String> = mutableListOf()
        for (string in input) {
            val indexODelimiter = string.indexOf("|")
            leftSides.add(string.substring(0, indexODelimiter))
            rightSides.add(string.substring(indexODelimiter+1))
        }

        var totalSum = 0
        for (index in 0 until leftSides.size step 1) {
            val segmentRule = SegmentRule(leftSides[index].trim().split(" ").toMutableList())
            val numberString = rightSides[index].trim().split(" ").toMutableList()
            var resultString = ""
            for (n in numberString) {
                resultString += segmentRule.getDigitForCode(n)
            }
            totalSum += resultString.toInt()
        }

        return totalSum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    checkEquals(part1(testInput), 26)
    println("test for part1 passed")
    val input = readInput("Day08")
    println("result for part1 >>> " + part1(input))
    checkEquals(part1(input), 495)
    println("-----")
    checkEquals(part2(testInput), 61229)
    println("test for part2 passed")
    println("result for part2 >>> " + part2(input))
    checkEquals(part2(input), 1055164)
}
