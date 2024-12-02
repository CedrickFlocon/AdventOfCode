import kotlin.math.absoluteValue

fun main() {
    day01PartOne()
    day01PartTwo()
}

fun day01PartOne() {
    resolve(1, "11") { file ->
        val a = mutableListOf<Long>()
        val b = mutableListOf<Long>()
        file.readLines()
            .forEach {
                it.split("   ").let {
                    a.add(it[0].toLong())
                    b.add(it[1].toLong())
                }
            }
        a.sort()
        b.sort()
        a.mapIndexed { index, value -> (value - b[index]).absoluteValue }
            .sum()
            .toString()
    }.also { println("Part one : $it") }
}

fun day01PartTwo() {
    resolve(1, "31") { file ->
        val a = mutableListOf<Long>()
        val b = mutableListOf<Long>()
        file.readLines().forEach {
            it.split("   ").let {
                a.add(it[0].toLong())
                b.add(it[1].toLong())
            }
        }

        a.sumOf { number ->
            b.count { it == number } * number
        }.toString()
    }.also { println("Part two : $it") }
}
