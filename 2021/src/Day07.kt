import java.io.File
import kotlin.math.abs

fun main() {
    fun part1(input: List<Int>): Int {
        return input.sumOf { abs(it - input.sortedBy { it }[input.size / 2]) }
    }

    fun part2(input: List<Int>): Int {
        return (input.minOf { it }..input.maxOf { it })
            .map { position -> input.sumOf { (1..abs(it - position)).sum() } }
            .minOf { it }
    }

    val input = File("src", "Day07.txt").readText().split(",").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
