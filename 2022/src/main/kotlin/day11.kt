import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        class Monkey(
            val items: MutableList<Int>,
            var itemsCount: Int,
            val operation: (Int) -> Pair<Int, Int>,
        )

        val monkeys = input
            .chunked(7)
            .map {
                val startingItems = "\\d+".toRegex().findAll(it[1]).map { it.value.toInt() }.toList()
                val monkeyToWorry = { worry: Int ->
                    val by = if (it[2].endsWith("old")) {
                        worry
                    } else {
                        "\\d+".toRegex().findAll(it[2]).map { it.value.toInt() }.first()
                    }
                    val newWorry = (when {
                        it[2].any { it == '*' } -> worry * by
                        it[2].any { it == '+' } -> worry + by
                        else -> throw UnsupportedOperationException(it[2])
                    } / 3.0).toInt()

                    val divisible = "\\d+".toRegex().findAll(it[3]).map { it.value.toInt() }.first()
                    val monkey = if (newWorry % divisible == 0) {
                        "\\d+".toRegex().findAll(it[4]).map { it.value.toInt() }.first()
                    } else {
                        "\\d+".toRegex().findAll(it[5]).map { it.value.toInt() }.first()
                    }

                    monkey to newWorry
                }

                Monkey(startingItems.toMutableList(), 0, monkeyToWorry)
            }
            .toTypedArray()

        (0 until 20).forEach {
            monkeys.forEach { monkey ->
                val items = monkey.items.toMutableList().also { monkey.items.clear() }
                items.forEach {
                    val (newMonkey, newWorry) = monkey.operation(it)
                    monkeys[newMonkey].items.add(newWorry)
                    monkey.itemsCount++
                }
            }
        }

        return monkeys
            .map { it.itemsCount }
            .sortedDescending()
            .take(2)
            .reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        class Monkey(
            val items: MutableList<Long>,
            var itemsCount: Long,
            val operation: (Long, Long) -> Pair<Int, Long>,
            val divisible: Long,
        )

        val monkeys = input
            .chunked(7)
            .map {
                val startingItems = "\\d+".toRegex().findAll(it[1]).map { it.value.toLong() }.toList()
                val divisibleBy = "\\d+".toRegex().findAll(it[3]).map { it.value.toLong() }.first()
                val monkeyToWorry = { worry: Long, mod: Long ->
                    val by = if (it[2].endsWith("old")) {
                        worry
                    } else {
                        "\\d+".toRegex().findAll(it[2]).map { it.value.toLong() }.first()
                    }
                    val newWorry = when {
                        it[2].any { it == '*' } -> worry * by
                        it[2].any { it == '+' } -> worry + by
                        else -> throw UnsupportedOperationException(it[2])
                    } % mod

                    val monkey = if (newWorry % divisibleBy == 0L) {
                        "\\d+".toRegex().findAll(it[4]).map { it.value.toInt() }.first()
                    } else {
                        "\\d+".toRegex().findAll(it[5]).map { it.value.toInt() }.first()
                    }

                    monkey to newWorry
                }

                Monkey(startingItems.toMutableList(), 0, monkeyToWorry, divisibleBy)
            }
            .toTypedArray()

        val mod = monkeys.map { it.divisible }.reduce { acc, i -> acc * i }

        (1..10000L).forEach {
            monkeys.forEach { monkey ->
                val items = monkey.items.toMutableList().also { monkey.items.clear() }
                items.forEach {
                    val (newMonkey, newWorry) = monkey.operation(it, mod)
                    monkeys[newMonkey].items.add(newWorry)
                    monkey.itemsCount++
                }
            }
        }

        return monkeys
            .map { it.itemsCount }
            .sortedDescending()
            .take(2)
            .reduce { acc, i -> acc * i }
    }

    val inputTest = File("src/main/kotlin/day11_test.txt").readLines()
    val input = File("src/main/kotlin/day11.txt").readLines()

    check(part1(inputTest) == 10605)
    println(part1(input))

    check(part2(inputTest) == 2713310158L)
    println(part2(input))
}