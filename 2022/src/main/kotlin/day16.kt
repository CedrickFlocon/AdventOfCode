import java.io.File
import java.util.*
import kotlin.math.max

fun main() {

    val pipeRegex = "[A-Z]{2}".toRegex()
    val pressureRegex = "(\\d+)".toRegex()

    fun part1(input: List<String>): Int {
        val scan = mutableMapOf<String, Pair<Int, List<String>>>()

        input.forEach {
            val pipe = pipeRegex.findAll(it)
            scan[pipe.first().value] = pressureRegex.find(it)!!.value.toInt() to pipe.drop(1).map { it.value }.toList()
        }

        var possible = listOf<Triple<String, Int, Set<String>>>(Triple("AA", 0, emptySet()))

        (1..30).forEach {
            possible = possible
                .flatMap { (position, pressureReleased, valveOpened) ->
                    val pipe = scan[position]!!
                    val newPressureReleased = pressureReleased + valveOpened.sumOf { scan[it]!!.first }

                    val moves = pipe.second
                        .map { Triple(it, newPressureReleased, valveOpened) }

                    val open = Triple(position, newPressureReleased, valveOpened + position)
                        .takeIf { pipe.first > 0 && valveOpened.none { it == position } }

                    (moves + open).filterNotNull()
                }
                .groupBy { it.first }
                .mapValues { it.value.groupBy { it.third }.map { it.value.maxBy { it.second } } }
                .values
                .flatten()
        }

        return possible.maxOf { it.second }
    }

    fun part2(input: List<String>): Int {
        val scan = mutableMapOf<String, Pair<Int, List<String>>>()

        input.forEach {
            val pipe = pipeRegex.findAll(it)
            scan[pipe.first().value] = pressureRegex.find(it)!!.value.toInt() to pipe.drop(1).map { it.value }.toList()
        }

        var possible = listOf<Triple<Pair<String, String>, Int, SortedSet<String>>>(Triple("AA" to "AA", 0, sortedSetOf()))

        for (minute in 1..26) {
            val maxPossible = possible.maxOf { it.second + ((26 - minute) * it.third.sumOf { scan[it]!!.first }) }
            possible = possible
                .filter {
                    val acquired = it.second + ((26 - minute) * it.third.sumOf { scan[it]!!.first })
                    val max = (scan.keys - it.third)
                        .map { scan[it]!!.first }
                        .sortedDescending()
                        .chunked(2)
                        .withIndex()
                        .sumOf { max(26 - minute - (it.index * 2) - 1, 0) * it.value.sumOf { it } }

                    acquired + max >= maxPossible
                }
                .flatMap { (positions, pressureReleased, valveOpened) ->
                    val newPressureReleased = pressureReleased + valveOpened.sumOf { scan[it]!!.first }

                    (scan[positions.first]!!.second
                        .flatMap { firstPossibility ->
                            scan[positions.second]!!.second
                                .map { secondPossibility ->
                                    Triple(
                                        firstPossibility to secondPossibility,
                                        newPressureReleased,
                                        valveOpened
                                    )
                                }
                        } +
                            scan[positions.second]!!.second.map {
                                Triple(positions.first to it, newPressureReleased, sortedSetOf(*(valveOpened + positions.first).toTypedArray()))
                                    .takeIf { scan[positions.first]!!.first > 0 && valveOpened.none { it == positions.first } }
                            } +
                            scan[positions.first]!!.second.map {
                                Triple(it to positions.second, newPressureReleased, sortedSetOf(*(valveOpened + positions.second).toTypedArray()))
                                    .takeIf { scan[positions.second]!!.first > 0 && valveOpened.none { it == positions.second } }
                            } +
                            Triple(positions, newPressureReleased, sortedSetOf(*(valveOpened + positions.toList()).toTypedArray()))
                                .takeIf { positions.toList().all { position -> scan[position]!!.first > 0 && valveOpened.none { it == position } } }
                            ).filterNotNull()
                }
                .groupBy { listOf(it.first.first, it.first.second).sorted().joinToString() }
                .mapValues { group ->
                    val values = mutableListOf<Triple<Pair<String, String>, Int, SortedSet<String>>>()
                    for (step in group.value.sortedByDescending { it.second }) {
                        values.removeAll { step.third.containsAll(it.third) && step.second > it.second }
                        if (values.none { it.third.containsAll(step.third) || step.second > it.second }) {
                            values.add(step)
                        }
                    }
                    values
                }
                .values
                .flatten()
        }

        return possible.maxOf { it.second }
    }

    val inputTest = File("src/main/kotlin/day16_test.txt").readLines()
    val input = File("src/main/kotlin/day16.txt").readLines()

    check(part1(inputTest) == 1651)
    println(part1(input))

    check(part2(inputTest) == 1707)
    println(part2(input))
}