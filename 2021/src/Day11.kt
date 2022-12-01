import java.io.File

fun main() {
    fun part1(input: List<MutableList<Int>>): Int {
        val findNeighbour = { row: Int, column: Int ->
            listOf(
                row to column - 1,
                row to column + 1,
                row - 1 to column,
                row + 1 to column,
                row + 1 to column + 1,
                row + 1 to column - 1,
                row - 1 to column - 1,
                row - 1 to column + 1,
            ).filter { it.first in input.indices && it.second in input[0].indices }
        }

        var flashNumber = 0
        (0 until 100).forEach {
            for (row in input.indices) {
                for (column in input[row].indices) {
                    input[row][column] = input[row][column] + 1
                }
            }

            while (input.any { it.any { it > 9 } }) {
                for (row in input.indices) {
                    for (column in input[row].indices) {
                        if (input[row][column] > 9) {
                            input[row][column] = 0
                            flashNumber++
                            findNeighbour(row, column).forEach {
                                if (input[it.first][it.second] != 0) {
                                    input[it.first][it.second] = input[it.first][it.second] + 1
                                }
                            }
                        }
                    }
                }
            }
        }

        return flashNumber
    }

    fun part2(input: List<MutableList<Int>>): Int {
        val findNeighbour = { row: Int, column: Int ->
            listOf(
                row to column - 1,
                row to column + 1,
                row - 1 to column,
                row + 1 to column,
                row + 1 to column + 1,
                row + 1 to column - 1,
                row - 1 to column - 1,
                row - 1 to column + 1,
            ).filter { it.first in input.indices && it.second in input[0].indices }
        }

        var step = 0
        while (true) {
            step++
            for (row in input.indices) {
                for (column in input[row].indices) {
                    input[row][column] = input[row][column] + 1
                }
            }

            var flashNumber = 0
            while (input.any { it.any { it > 9 } }) {
                for (row in input.indices) {
                    for (column in input[row].indices) {
                        if (input[row][column] > 9) {
                            input[row][column] = 0
                            flashNumber++
                            findNeighbour(row, column).forEach {
                                if (input[it.first][it.second] != 0) {
                                    input[it.first][it.second] = input[it.first][it.second] + 1
                                }
                            }
                        }
                    }
                }
            }

            if (flashNumber == 100) {
                return step
            }
        }
    }

    val input = File("src", "Day11.txt").readLines().map { it.map { it.digitToInt() } }
    println(part1(input.map { it.toMutableList() }))
    println(part2(input.map { it.toMutableList() }))
}
