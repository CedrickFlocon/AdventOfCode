import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .first()
            .windowed(4)
            .indexOfFirst { it.toSet().size == 4 } + 4
    }

    fun part2(input: List<String>): Int {
        return input
            .first()
            .windowed(14)
            .indexOfFirst { it.toSet().size == 14 } + 14
    }

    val inputTest = File("src/main/kotlin/day06_test.txt").readLines()
    val input = File("src/main/kotlin/day06.txt").readLines()

    check(part1(inputTest) == 7)
    println(part1(input))

    check(part2(inputTest) == 19)
    println(part2(input))
}