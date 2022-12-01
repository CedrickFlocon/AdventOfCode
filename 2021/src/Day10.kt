import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        val chunks = listOf(
            Triple('(', ')', 3),
            Triple('[', ']', 57),
            Triple('{', '}', 1197),
            Triple('<', '>', 25137),
        )

        return input.map {
            val openedChunk = mutableListOf<Char>()
            it.forEach { char ->
                if (chunks.find { it.first == char } != null) {
                    openedChunk.add(char)
                } else if (chunks.find { it.second == char }!!.first == openedChunk.last()) {
                    openedChunk.removeLast()
                } else {
                    return@map chunks.find { it.second == char }!!.third
                }
            }
            return@map 0
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val chunks = listOf(
            Triple('(', ')', 1),
            Triple('[', ']', 2),
            Triple('{', '}', 3),
            Triple('<', '>', 4),
        )

        val scores = input
            .map {
                val openedChunk = mutableListOf<Char>()
                it.forEach { char ->
                    if (chunks.find { it.first == char } != null) {
                        openedChunk.add(char)
                    } else if (chunks.find { it.second == char }!!.first == openedChunk.last()) {
                        openedChunk.removeLast()
                    } else {
                        return@map 0
                    }
                }
                openedChunk.reversed().fold(0L) { acc, c -> acc * 5 + chunks.find { it.first == c }!!.third }
            }
            .filter { it != 0L }
            .sorted()

        return scores[scores.size / 2]
    }

    val input = File("src", "Day10.txt").readLines()
    println(part1(input))
    println(part2(input))
}
