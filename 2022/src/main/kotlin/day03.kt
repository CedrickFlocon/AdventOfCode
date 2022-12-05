import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.chunked(it.length / 2) }
            .map { (first, second) -> first.first { char -> second.any { it == char } } }
            .sumOf { it.toPrioritize() }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .mapNotNull { rucksack ->
                rucksack.first().find { char ->
                    rucksack.drop(1).all { it.any { it == char } }
                }
            }.sumOf { it.toPrioritize() }
    }

    val inputTest = File("src/main/kotlin/day03_test.txt").readLines()
    val input = File("src/main/kotlin/day03.txt").readLines()

    check(part1(inputTest) == 157)
    println(part1(input))

    check(part2(inputTest) == 70)
    println(part2(input))
}

private fun Char.toPrioritize() = if (this.isLowerCase()) {
    this.code - 'a'.code + 1
} else {
    this.code - 'A'.code + 1 + 26
}
