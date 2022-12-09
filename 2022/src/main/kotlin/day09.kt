import java.io.File
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    data class Coordinate(val x: Long, val y: Long) {
        fun touching(coordinate: Coordinate): Boolean {
            return (abs(y - coordinate.y) <= 1) && (abs(x - coordinate.x) <= 1)
        }
    }

    fun part1(input: List<String>): Int {
        return input
            .flatMap { it.split(" ").let { action -> List(action[1].toInt()) { action[0] } } }
            .scan(Coordinate(0, 0) to Coordinate(0, 0)) { acc, direction ->
                val head = when (direction) {
                    "R" -> acc.first.copy(x = acc.first.x + 1)
                    "L" -> acc.first.copy(x = acc.first.x - 1)
                    "U" -> acc.first.copy(y = acc.first.y + 1)
                    "D" -> acc.first.copy(y = acc.first.y - 1)
                    else -> throw IllegalArgumentException()
                }

                val touching = (abs(head.y - acc.second.y) <= 1) && (abs(head.x - acc.second.x) <= 1)
                val align = head.x == acc.second.x || head.y == acc.second.y

                val tail = if (!touching) {
                    when (direction) {
                        "R" -> acc.second.copy(
                            x = acc.second.x + 1,
                            y = if (!align) head.y else acc.second.y
                        )
                        "L" -> acc.second.copy(
                            x = acc.second.x - 1,
                            y = if (!align) head.y else acc.second.y
                        )
                        "U" -> acc.second.copy(
                            y = acc.second.y + 1,
                            x = if (!align) head.x else acc.second.x
                        )
                        "D" -> acc.second.copy(
                            y = acc.second.y - 1,
                            x = if (!align) head.x else acc.second.x
                        )
                        else -> throw IllegalArgumentException()
                    }
                } else {
                    acc.second
                }

                head to tail
            }
            .map { it.second }
            .distinct()
            .count()
    }

    fun part2(input: List<String>): Int {
        return input
            .flatMap { it.split(" ").let { action -> List(action[1].toInt()) { action[0] } } }
            .scan(List(10) { Coordinate(0, 0) }) { acc, direction ->
                acc.scanIndexed(acc.first()) { index, previous, current ->
                    if (index == 0) return@scanIndexed when (direction) {
                        "R" -> current.copy(x = current.x + 1)
                        "L" -> current.copy(x = current.x - 1)
                        "U" -> current.copy(y = current.y + 1)
                        "D" -> current.copy(y = current.y - 1)
                        else -> throw IllegalArgumentException()
                    }

                    val shift = { prev: Long, cur: Long -> cur + (prev - cur).sign }
                    current.takeIf { current.touching(previous) }
                        ?: current.copy(
                            x = shift(previous.x, current.x),
                            y = shift(previous.y, current.y)
                        )
                }.drop(1)
            }
            .map { it.last() }
            .distinct()
            .count()
    }

    val inputTest1 = File("src/main/kotlin/day09-1_test.txt").readLines()
    val input = File("src/main/kotlin/day09.txt").readLines()

    check(part1(inputTest1) == 13)
    println(part1(input))

    val inputTest2 = File("src/main/kotlin/day09-2_test.txt").readLines()
    check(part2(inputTest2) == 36)
    println(part2(input))
}

