import java.io.File
import kotlin.math.roundToLong

fun main() {
    fun part1(input: List<String>): Int {
        var template = input.first()

        val rules = input
            .map { it.split(" -> ") }
            .filter { it.size == 2 }
            .map { it[0] to it[1] }
            .toMap()

        repeat(10) {
            template = template
                .windowed(2) { windowed -> rules[windowed]?.let { rule -> "${windowed.first()}${rule}${windowed.last()}" } ?: windowed }
                .reduce { acc, s -> acc.toString() + s.drop(1) }
                .toString()
        }

        return template.groupBy { it }.let { it.maxOf { it.value.size } - it.minOf { it.value.size } }
    }

    fun part2(input: List<String>): Long {
        val rules = input.map { it.split(" -> ") }
            .filter { it.size == 2 }
            .map { it[0] to listOf(it[0].first() + it[1], it[1] + it[0].last()) }
            .toMap()

        var pairs = input.first()
            .windowed(2)
            .map { it to 1L }

        repeat(40) {
            pairs = pairs
                .flatMap { pair -> rules[pair.first]?.let { it.map { it to pair.second } } ?: listOf(pair) }
                .groupBy { it.first }
                .map { it.key to it.value.sumOf { it.second } }
        }

        return pairs
            .flatMap { listOf(it.first[0] to it.second, it.first[1] to it.second) }
            .groupBy { it.first }.map { it.key to (it.value.sumOf { it.second } / 2.0).roundToLong() }.let { letters ->
                letters.maxOf { it.second } - letters.minOf { it.second }
            }
    }

    val input = File("src", "Day14.txt").readLines()
    println(part1(input))
    println(part2(input))
}
