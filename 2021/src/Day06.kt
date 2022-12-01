import java.io.File

fun main() {
    fun part1(input: List<Int>): Int {
        var lanternfish = input
        (1..80).forEach { day ->
            lanternfish = lanternfish.flatMap {
                if (it == 0) {
                    listOf(6, 8)
                } else {
                    listOf(it - 1)
                }
            }
        }
        return lanternfish.size
    }

    fun part2(input: List<Int>): ULong {
        var lanternfish = input.groupBy { it }.mapValues { it.value.size.toULong() }
        (1..256).forEach { day ->
            lanternfish.mapKeys { it.key - 1 }.let {
                val deliveryCount = it.getOrDefault(-1, 0U)

                lanternfish = it.filter { it.key != -1 } +
                        Pair(6, deliveryCount + it.getOrDefault(6, 0U)) +
                        Pair(8, deliveryCount + it.getOrDefault(8, 0U))
            }

        }
        return lanternfish.map { it.value }.sum()
    }

    val input = File("src", "Day06.txt").readText().split(",").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
