fun main() {
    day11PartOne()
    day11PartTwo()
}

fun day11PartOne() {
    resolve(11, "55312") { file ->
        var stones = file.readLines()
            .first()
            .split(" ")
            .map { it.toLong() }

        repeat(25) {
            stones = stones.fold(emptyList()) { acc, value ->
                if (value == 0L) return@fold acc + listOf(1)

                val v = value.toString()

                if (v.length % 2 == 0) {
                    acc + listOf(v.substring(0, v.length / 2).toLong(), v.substring(v.length / 2).toLong())
                } else {
                    acc + listOf(value * 2024)
                }
            }
        }

        stones.size.toString()
    }.also { println("Part one : $it") }
}

fun day11PartTwo() {
    resolve(11, "65601038650482") { file ->
        var stones = file.readLines()
            .first()
            .split(" ")
            .map { it.toLong() }
            .groupBy { it }
            .mapValues { it.value.size.toLong() }

        repeat(75) {
            stones = stones.entries.fold(mutableMapOf()) { acc, entry ->
                if (entry.key == 0L) {
                    acc.merge(1, entry.value, Long::plus)
                } else {
                    val v = entry.key.toString()

                    if (v.length % 2 == 0) {
                        acc.merge(v.substring(0, v.length / 2).toLong(), entry.value, Long::plus)
                        acc.merge(v.substring(v.length / 2).toLong(), entry.value, Long::plus)
                    } else {
                        acc.merge(entry.key * 2024, entry.value, Long::plus)
                    }
                }
                acc
            }
        }

        stones
            .map { it.value }
            .sum()
            .toString()
    }.also { println("Part two : $it") }
}
