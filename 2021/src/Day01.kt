import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(2)
            .count { it[0] < it[1] }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(4)
            .count { it[0] < it[3] }
    }

    val input = File("src", "Day01.txt").readLines()
    println(part1(input))
    println(part2(input))
}
