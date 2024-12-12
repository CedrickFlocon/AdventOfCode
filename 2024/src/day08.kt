fun main() {
    day08PartOne()
    day08PartTwo()
}

fun day08PartOne() {
    resolve(8, "14") { file ->
        val lines = file.readLines()
        val width = lines[0].length
        val height = lines.size

        lines
            .withIndex()
            .flatMap { (i, line) ->
                line.withIndex()
                    .filter { it.value != '.' }
                    .map { (j, char) -> (i to j) to char }
            }
            .groupBy({ it.second }, { it.first })
            .flatMap { (_, coordinates) ->
                coordinates
                    .flatMap { c -> coordinates.filter { it != c }.map { c to it } }
                    .flatMap { (a, b) ->
                        val vector = (a.first - b.first) to (a.second - b.second)
                        listOf(
                            a.first + vector.first to a.second + vector.second, b.first - vector.first to b.second - vector.second
                        )
                    }
            }
            .distinct()
            .count { it.first in 0..<height && it.second in 0..<width }
            .toString()
    }.also { println("Part one : $it") }
}

fun day08PartTwo() {
    resolve(8, "34") { file ->
        val lines = file.readLines()
        val width = lines[0].length
        val height = lines.size
        val move: Pair<Int, Int>.(v: Pair<Int, Int>) -> Pair<Int, Int> = { v: Pair<Int, Int> -> first + v.first to second + v.second }
        lines
            .withIndex()
            .flatMap { (i, line) ->
                line.withIndex()
                    .filter { it.value != '.' }
                    .map { (j, char) -> (i to j) to char }
            }
            .groupBy({ it.second }, { it.first })
            .flatMap { (_, coordinates) ->
                coordinates
                    .flatMap { c -> coordinates.filter { it != c }.map { c to it } }
                    .flatMap { (a, b) ->
                        val vector = (a.first - b.first) to (a.second - b.second)
                        val antinodes = mutableSetOf<Pair<Int, Int>>()
                        for (pair in listOf(a, b)) {
                            antinodes.add(pair)
                            while (true) {
                                val antinode = antinodes.last().move(vector)
                                if (antinode.first !in 0..<height && antinode.second !in 0..<width || antinodes.contains(antinode)) break
                                antinodes.add(antinode)
                            }
                        }
                        antinodes
                    }
            }
            .distinct()
            .count { it.first in 0..<height && it.second in 0..<width }
            .toString()
    }.also { println("Part two : $it") }
}
