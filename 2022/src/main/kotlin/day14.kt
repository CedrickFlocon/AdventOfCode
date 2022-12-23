import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    val EMPTY = ' '
    val ROCK = '#'
    val SAND = 'o'

    fun part1(input: List<String>): Int {
        val scan = Array(200) { CharArray(600) { EMPTY } }

        input
            .flatMap {
                it
                    .split(" -> ")
                    .map { it.split(',').let { it[0].toInt() to it[1].toInt() } }
                    .windowed(2)
            }
            .map { it[0] to it[1] }
            .forEach {
                (min(it.first.first, it.second.first)..max(it.first.first, it.second.first)).forEach { c ->
                    (min(it.first.second, it.second.second)..max(it.first.second, it.second.second)).forEach { l ->
                        scan[l][c] = ROCK
                    }
                }
            }

        var sand = 0
        sand@ while (true) {
            sand++
            var sandPosition = 0 to 500
            stable@ while (true) {
                val moveOrder = listOf(
                    sandPosition.first + 1 to sandPosition.second,
                    sandPosition.first + 1 to sandPosition.second - 1,
                    sandPosition.first + 1 to sandPosition.second + 1,
                )

                for (move in moveOrder) {
                    val tile = scan.getOrNull(move.first)?.getOrNull(move.second)
                        ?: break@sand
                    if (tile == EMPTY) {
                        sandPosition = move
                        continue@stable
                    }
                }

                scan[sandPosition.first][sandPosition.second] = SAND
                break
            }
        }

        return sand - 1
    }

    fun part2(input: List<String>): Int {
        val scan = Array(1000) { CharArray(1000) { EMPTY } }

        var maxL = 0

        input
            .flatMap {
                it
                    .split(" -> ")
                    .map { it.split(',').let { it[0].toInt() to it[1].toInt() } }
                    .windowed(2)
            }
            .map { it[0] to it[1] }
            .forEach {
                (min(it.first.first, it.second.first)..max(it.first.first, it.second.first)).forEach { c ->
                    (min(it.first.second, it.second.second)..max(it.first.second, it.second.second)).forEach { l ->
                        scan[l][c] = ROCK

                        maxL = max(maxL, l)
                    }
                }
            }

        scan[maxL + 2] = CharArray(1000) { '#' }

        var sand = 0
        sand@ while (true) {
            sand++
            var sandPosition = 0 to 500
            stable@ while (true) {
                val moveOrder = listOf(
                    sandPosition.first + 1 to sandPosition.second,
                    sandPosition.first + 1 to sandPosition.second - 1,
                    sandPosition.first + 1 to sandPosition.second + 1,
                )

                for (move in moveOrder) {
                    val tile = scan[move.first][move.second]
                    if (tile == EMPTY) {
                        sandPosition = move
                        continue@stable
                    }
                }

                scan[sandPosition.first][sandPosition.second] = SAND
                break
            }

            if (scan[0][500] == SAND) {
                return sand
            }
        }
    }

    val inputTest = File("src/main/kotlin/day14_test.txt").readLines()
    val input = File("src/main/kotlin/day14.txt").readLines()

    check(part1(inputTest) == 24)
    println(part1(input))

    check(part2(inputTest) == 93)
    println(part2(input))
}