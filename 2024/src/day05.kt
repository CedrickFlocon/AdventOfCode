fun main() {
    day05PartOne()
    day05PartTwo()
}

fun day05PartOne() {
    resolve(5, "143") { file ->
        val rules = file.readLines()
            .filter { it.contains("|") }
            .map { it.split("|") }
            .map { it[0].toLong() to it[1].toLong() }
            .groupBy({ it.first }, { it.second })

        val updates = file.readLines()
            .filter { it.contains(",") }
            .map { it.split(",").map { it.toLong() } }

        var result = 0L
        updates.forEach {
            if (it.windowed(2).all { (a, b) -> rules[a]?.any { it == b } == true }) {
                result += it[it.size / 2]
            }
        }

        result.toString()
    }.also { println("Part one : $it") }
}

fun day05PartTwo() {
    resolve(5, "123") { file ->
        val rules = file.readLines()
            .filter { it.contains("|") }
            .map { it.split("|") }
            .map { it[0].toLong() to it[1].toLong() }
            .groupBy({ it.first }, { it.second })

        val updates = file.readLines()
            .filter { it.contains(",") }
            .map { it.split(",").map { it.toLong() } }

        var result = 0L
        updates
            .filter { it.windowed(2).any { (a, b) -> rules[a]?.none { it == b } != false } }
            .forEach { update ->
                var importantRule = rules
                    .filter { rule -> update.any { rule.key == it } }
                    .mapValues { it.value.filter { value -> update.any { value == it } } }
                    .filter { it.value.isNotEmpty() }

                val newUpdate = mutableListOf<Long>()
                newUpdate.add(0, update.first { it !in importantRule.keys })

                while (true) {
                    importantRule = importantRule
                        .filter { it.key != newUpdate.first() }
                        .mapValues { it.value.filter { it != newUpdate.first() } }

                    newUpdate.add(0, importantRule.filter { it.value.isEmpty() }.keys.first())
                    if (newUpdate.size == update.size) break
                }

                result += newUpdate[newUpdate.size / 2]
            }

        result.toString()
    }.also { println("Part two : $it") }
}
