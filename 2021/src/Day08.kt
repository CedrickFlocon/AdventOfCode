import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.split("|")[1] }
            .flatMap { it.split(" ") }
            .count { it.length in listOf(2, 4, 3, 7) }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split("|") }.map {
            val patterns = it[0].trim().split(" ").map { it.toCharArray().sorted().joinToString("") }

            val one = patterns.first { it.length == 2 } to 1
            val four = patterns.first { it.length == 4 } to 4
            val seven = patterns.first { it.length == 3 } to 7
            val height = patterns.first { it.length == 7 } to 8
            val six = patterns.first { it.length == 6 && (it + one.first).toCharArray().distinct().size == 7 } to 6
            val nine = patterns.first { pattern -> pattern.length == 6 && four.first.all { pattern.contains(it) } && pattern != six.first } to 9
            val zero = patterns.first { it.length == 6 && it != six.first && it != nine.first } to 0
            val three = patterns.first { pattern -> pattern.length == 5 && one.first.all { pattern.contains(it) } } to 3
            val five = patterns.first { it.length == 5 && (it + six.first).toCharArray().distinct().size == 6 } to 5
            val two = patterns.first { it.length == 5 && it != three.first && it != five.first } to 2
            val decoder = mapOf(one, four, seven, height, six, nine, zero, three, five, two)

            it[1].trim().split(" ")
                .map { it.toCharArray().sorted().joinToString("") }
                .mapNotNull { decoder[it] }
                .joinToString("").toInt()
        }.sum()
    }

    val input = File("src", "Day08.txt").readLines()

    println(part1(input))
    println(part2(input))

    check(983026 == part2(input))

}
