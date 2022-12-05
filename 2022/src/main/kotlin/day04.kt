import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.split(',').map { it.split('-').map { it.toInt() } } }
            .map { it[0][0]..it[0][1] to it[1][0]..it[1][1] }
            .count { (first, second) -> first.first in second && first.last in second || second.first in first && second.last in first }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(',').map { it.split('-').map { it.toInt() } } }
            .map { it[0][0]..it[0][1] to it[1][0]..it[1][1] }
            .map { it.first to it.second }
            .count { (first, second) -> first.first in second || first.last in second || second.first in first || second.last in first }
    }

    val inputTest = File("src/main/kotlin/day04_test.txt").readLines()
    val input = File("src/main/kotlin/day04.txt").readLines()

    check(part1(inputTest) == 2)
    println(part1(input))

    check(part2(inputTest) == 4)
    println(part2(input))
}