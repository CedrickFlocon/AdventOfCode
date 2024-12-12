import java.lang.Math.pow

fun main() {
    day07PartOne()
    day07PartTwo()
}

fun day07PartOne() {
    resolve(7, "3749") { file ->
        val possibleOperation = listOf('+', '*')

        file
            .readLines()
            .map { it.filterNot { it == ':' }.split(" ").map { it.toLong() } }
            .filter {
                val result = it.first()
                val numbers = it.drop(1)

                (0 until pow(possibleOperation.size.toDouble(), (numbers.size - 1).toDouble()).toInt())
                    .map { Integer.toBinaryString(it).reversed() }
                    .filter { operations ->
                        numbers.reduceIndexed { index, acc, number ->
                            if (operations.getOrElse(index - 1) { '0' } == '0') {
                                acc + number
                            } else {
                                acc * number
                            }
                        } == result
                    }.isNotEmpty()
            }
            .sumOf { it.first() }
            .toString()
    }.also { println("Part one : $it") }
}

fun day07PartTwo() {
    resolve(7, "11387") { file ->
        file.readLines()
            .map { it.filterNot { it == ':' }.split(" ").map { it.toLong() } }
            .filter {
                val result = it.first()
                val numbers = it.drop(1)

                numbers.drop(1)
                    .fold(List(3) { numbers.first() }) { acc, v ->
                        acc
                            .flatMap { l: Long -> listOf(l + v, l * v, (l.toString() + v.toString()).toLong()) }
                            .filter { it <= result }
                    }.any { it == result }
            }
            .sumOf { it.first() }
            .toString()
    }.also { println("Part two : $it") }
}
