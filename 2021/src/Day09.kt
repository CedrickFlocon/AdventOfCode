import java.io.File

fun main() {
    fun part1(input: List<List<Int>>): Int {
        return input.mapIndexed { y, ints ->
            ints.mapIndexed { x, i ->
                val left = input[y].getOrNull(x - 1) ?: 10
                val right = input[y].getOrNull(x + 1) ?: 10
                val up = input.getOrNull(y - 1)?.get(x) ?: 10
                val down = input.getOrNull(y + 1)?.get(x) ?: 10

                if (i < left && i < right && i < up && i < down) {
                    i + 1
                } else {
                    0
                }
            }
        }.sumOf { it.sum() }
    }

    fun part2(input: List<List<Int>>): Int {
        val findNeighbour = { row: Int, column: Int ->
            listOf(
                row to column - 1,
                row to column + 1,
                row - 1 to column,
                row + 1 to column
            ).filter { it.first in input.indices && it.second in input[0].indices }
        }

        return input
            .mapIndexed { row, ints ->
                ints.mapIndexedNotNull { column, i ->
                    if (findNeighbour(row, column).map { input[it.first][it.second] }.none { i >= it }) {
                        row to column
                    } else {
                        null
                    }
                }
            }
            .flatten()
            .map { (row, column) ->
                val basin = mutableListOf<Pair<Int, Int>>()
                val stack = ArrayDeque(listOf(row to column))
                while (stack.isNotEmpty()) {
                    val point = stack.removeFirst()
                    basin.add(point)
                    findNeighbour(point.first, point.second)
                        .filter { input[it.first][it.second] < 9 && input[point.first][point.second] < input[it.first][it.second] }
                        .forEach { stack.addLast(it) }
                }
                basin.distinct().size
            }.sortedDescending()
            .take(3)
            .reduce(Int::times)
    }

    val input = File("src", "Day09.txt").readLines().map { it.map { it.digitToInt() } }
    println(part1(input))
    println(part2(input))
}
