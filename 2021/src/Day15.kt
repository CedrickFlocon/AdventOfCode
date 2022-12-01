import java.io.File
import java.util.Stack
import kotlin.math.min

fun main() {

    val input = File("src", "Day15.txt")
        .readLines()
        .map { it.toCharArray().map { it.digitToInt() }.toTypedArray() }
        .toTypedArray()
    val size = input.size - 1

    val findNeighbour = { row: Int, column: Int ->
        listOf(
            row to column - 1,
            row to column + 1,
            row - 1 to column,
            row + 1 to column,
        ).filter { it.first in input.indices && it.second in input[0].indices }
    }

    fun List<Pair<Int, Int>>.risk() = this.sumOf { input[it.first][it.second] }

    fun part1(): Int {
        val paths = Stack<List<Pair<Int, Int>>>().also { it.push(listOf(0 to 0)) }
        var minRisk = ((0..size).map { it to 0 } + (0..size).map { 0 to it }).risk()

        do {
            val path = paths.removeFirst()
            val (x, y) = path.last()

            findNeighbour(x, y)
                .filter { neighbour -> path.none { it == neighbour } }
                .map { path + it }
                .forEach { possiblePath ->
                    if (possiblePath.last() == size to size) {
                        minRisk = min(minRisk, possiblePath.risk())
                    } else if (paths.none { p -> (possiblePath).all { p.contains(it) } }) {
                        paths.push(possiblePath)
                    }
                }
            paths.removeAll { it.risk() >= minRisk }
            paths.removeAll { a -> paths.any { b -> a != b && b.contains(a.last()) && a.risk() >= b.risk() } }
        } while (paths.isNotEmpty())

        return minRisk
    }

    fun part2(): Int {
        throw NotImplementedError()
    }

    println(part1() - 1)
}
