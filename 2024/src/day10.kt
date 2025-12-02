import java.util.*

fun main() {
    day10PartOne()
    day10PartTwo()
}

fun day10PartOne() {
    resolve(10, "36") { file ->
        val move = listOf<(Pair<Int, Int>) -> Pair<Int, Int>>(
            { (i, j) -> i to j + 1 },
            { (i, j) -> i to j - 1 },
            { (i, j) -> i - 1 to j },
            { (i, j) -> i + 1 to j }
        )

        val map = file.readLines()
            .withIndex()
            .flatMap { (i, line) -> line.withIndex().map { Triple(i, it.index, it.value.digitToInt()) } }
            .associateBy({ it.first to it.second }, { it.third })

        map.filter { it.value == 0 }
            .map {
                val points = LinkedList(listOf(it.key))
                val end = mutableSetOf<Pair<Int, Int>>()
                while (points.isNotEmpty()) {
                    val point = points.pop()
                    val nextPoint = move.map { it.invoke(point) }
                        .filter { (map[point]!! + 1) == map[it] }

                    end.addAll(nextPoint.filter { map[it] == 9 })
                    points.addAll(nextPoint.filter { map[it]!! < 9 })
                }
                end.count()
            }
            .sum()
            .toString()
    }.also { println("Part one : $it") }
}

fun day10PartTwo() {
    resolve(10, "81") { file ->
        val move = listOf<(Pair<Int, Int>) -> Pair<Int, Int>>(
            { (i, j) -> i to j + 1 },
            { (i, j) -> i to j - 1 },
            { (i, j) -> i - 1 to j },
            { (i, j) -> i + 1 to j }
        )

        val map = file.readLines()
            .withIndex()
            .flatMap { (i, line) -> line.withIndex().map { Triple(i, it.index, it.value.digitToInt()) } }
            .associateBy({ it.first to it.second }, { it.third })

        val points = LinkedList(map.filter { it.value == 0 }.toList())
        var path = 0
        while (points.isNotEmpty()) {
            val point = points.pop()
            val nextPoint = move.map { it.invoke(point.first) }
                .map { it to map.getOrElse(it) { -10 } }
                .filter { point.second + 1 == it.second }

            path += nextPoint.count { it.second == 9 }
            points.addAll(nextPoint.filter { it.second > 0 })
        }

        path.toString()
    }.also { println("Part two : $it") }
}
