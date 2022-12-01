import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        val actions = input
            .map { it.split(" ") }
            .groupBy({ it[0] }, { it[1].toInt() })
            .mapValues { it.value.sum() }

        return actions["forward"]!! * (actions["down"]!! - actions["up"]!!)
    }

    fun part2(input: List<String>): Int {
        val actions = input
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }

        val aim = actions.scan(0) { acc, value ->
            when (value.first) {
                "down" -> acc + value.second
                "up" -> acc - value.second
                else -> acc
            }
        }

        return actions
            .filter { it.first == "forward" }
            .sumOf { it.second }
            .times(
                actions
                    .withIndex()
                    .filter { it.value.first == "forward" }
                    .fold(0) { acc, action -> acc + action.value.second * aim[action.index] }
            )
    }

    val input = File("src", "Day02.txt").readLines()
    println(part1(input))
    println(part2(input))
}
