import java.io.File

fun main() {
    val winning: (Array<Array<Int?>>) -> Boolean = { board ->
        board.any { it.all { it == null } } || (0..4).any { index -> board.all { it[index] == null } }
    }

    fun part1(numbers: List<Int>, boards: List<Array<Array<Int?>>>): Int {
        for (number in numbers) {
            for (board in boards) {
                line@ for (line in board) {
                    val index = line.indexOfFirst { it == number }
                    if (index != -1) {
                        line[index] = null
                        break@line
                    }
                }

                if (winning(board)) {
                    return board.sumOf { it.filterNotNull().sum() } * number
                }
            }
        }

        throw IllegalStateException()
    }

    fun part2(numbers: List<Int>, boards: MutableList<Array<Array<Int?>>>): Int {
        for (number in numbers) {
            for (board in boards) {
                line@ for (line in board) {
                    val index = line.indexOfFirst { it == number }
                    if (index != -1) {
                        line[index] = null
                        break@line
                    }
                }

                if (boards.size == 1 && winning(board)) {
                    return board.sumOf { it.filterNotNull().sum() } * number
                }
            }

            boards.removeIf(winning)
        }

        throw IllegalStateException()
    }

    val input = File("src", "Day04.txt").readLines()
    val numbers = input.first().split(",").map { it.toInt() }
    val boards = mutableListOf<Array<Array<Int?>>>()

    for (i in 2..input.size step 6) {
        boards.add(input.subList(i, i + 5).map {
            it.split(" ").filter { it.isNotBlank() }.map { it.toInt() as Int? }.toTypedArray()
        }.toTypedArray())
    }

    println(part1(numbers, boards))
    println(part2(numbers, boards))
}
