fun main() {
    day04PartOne()
    day04PartTwo()
}

fun day04PartOne() {
    resolve(4, "18") { file ->
        val word = "MAS"

        val direction = listOf<(Pair<Int, Int>) -> Pair<Int, Int>>(
            { (i, j) -> i to j + 1 },
            { (i, j) -> i - 1 to j + 1 },
            { (i, j) -> i - 1 to j },
            { (i, j) -> i - 1 to j - 1 },
            { (i, j) -> i to j - 1 },
            { (i, j) -> i + 1 to j - 1 },
            { (i, j) -> i + 1 to j },
            { (i, j) -> i + 1 to j + 1 },
        )
        val input = file.readLines()
            .map { it.toCharArray() }
            .toTypedArray()

        var occurrence = 0

        for ((i, chars) in input.withIndex()) {
            for ((j, _) in chars.withIndex()) {
                if (input[i][j] == 'X') {
                    occurrence += direction.map { transform ->
                        var coordinate = i to j
                        repeat(3) {
                            coordinate = transform.invoke(coordinate)
                            val char = input.getOrNull(coordinate.first)?.getOrNull(coordinate.second)
                            if (char != word[it]) {
                                return@map false
                            }
                        }
                        return@map true
                    }.count { it }
                }
            }
        }
        occurrence.toString()
    }.also { println("Part one : $it") }
}

fun day04PartTwo() {
    resolve(4, "9") { file ->
        val word = listOf('M', 'S')

        val first = listOf<(Pair<Int, Int>) -> Pair<Int, Int>>(
            { (i, j) -> i + 1 to j + 1 },
            { (i, j) -> i - 1 to j - 1 }
        )

        val second = listOf<(Pair<Int, Int>) -> Pair<Int, Int>>(
            { (i, j) -> i - 1 to j + 1 },
            { (i, j) -> i + 1 to j - 1 }
        )

        val input = file.readLines()
            .map { it.toCharArray() }
            .toTypedArray()

        var occurrence = 0

        for ((i, chars) in input.withIndex()) {
            for ((j, _) in chars.withIndex()) {
                if (input[i][j] == 'A') {
                    if (first.map { transform ->
                            val coordinate = transform(i to j)
                            input.getOrNull(coordinate.first)?.getOrNull(coordinate.second)
                        }.containsAll(word) &&
                        second.map { transform ->
                            val coordinate = transform(i to j)
                            input.getOrNull(coordinate.first)?.getOrNull(coordinate.second)
                        }.containsAll(word)
                    ) {
                        occurrence++
                    }
                }
            }
        }
        occurrence.toString()
    }.also { println("Part two : $it") }
}
