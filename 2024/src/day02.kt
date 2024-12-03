import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    day02PartOne()
    day02PartTwo()
}

fun day02PartOne() {
    resolve(2, "2") { file ->
        file
            .readLines()
            .map { it.split(" ").map { it.toInt() } }
            .map {
                val sign = compareValues(it[0], it[1]).sign.takeIf { it != 0 }
                it.windowed(2)
                    .map { (it[0] - it[1]).absoluteValue <= 3 && sign == compareValues(it[0], it[1]).sign }
                    .all { it }
            }.count { it }
            .toString()
    }.also { println("Part one : $it") }
}

fun day02PartTwo() {
    resolve(2, "4") { file ->
        val isSafe = { level: List<Int> ->
            val sign = compareValues(level[0], level[1]).sign.takeIf { it != 0 }
            level.windowed(2)
                .map { (it[0] - it[1]).absoluteValue <= 3 && sign == compareValues(it[0], it[1]).sign }
                .all { it }
        }

        file
            .readLines()
            .map { it.split(" ").map { it.toInt() } }
            .map {
                if (isSafe(it)) return@map true

                for (index in it.indices) {
                    if (isSafe(it.filterIndexed { i, _ -> i != index })) return@map true
                }

                return@map false
            }
            .count { it }
            .toString()
    }.also { println("Part two : $it") }
}
