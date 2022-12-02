import java.io.File

fun main() {
    fun part1(input: List<Int>): Int {
        return input.max()
    }

    fun part2(input: List<Int>): Int {
        return input
            .sortedDescending()
            .take(3)
            .sum()
    }

    val input = File("src/main/kotlin/day01.txt")
        .readLines()
        .fold(mutableListOf(mutableListOf<Int>())) { acc, calorie ->
            if (calorie.isEmpty()) acc.add(mutableListOf())
            else acc.last().add(calorie.toInt())
            acc
        }
        .map { it.sum() }

    println(part1(input))
    println(part2(input))
}