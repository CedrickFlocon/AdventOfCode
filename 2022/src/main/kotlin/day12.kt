import java.io.File

fun main() {
    operator fun <T> Array<Array<T>>.get(pair: Pair<Int, Int>) = this[pair.first][pair.second]
    operator fun <T> Array<Array<T>>.set(pair: Pair<Int, Int>, value: T) {
        this[pair.first][pair.second] = value
    }

    fun part1(input: List<String>): Int {
        val heightmap = input.map { it.toCharArray().toTypedArray() }.toTypedArray()

        val flatten = heightmap.flatten()
        var beginnings = listOf(flatten.indexOf('S')
            .let { it / input.first().count() to it % input.first().count() }
            .also { heightmap[it] = 'a' }
        )
        val end = flatten.indexOf('E')
            .let { it / input.first().count() to it % input.first().count() }
            .also { heightmap[it] = 'z' }

        val next = { c: Pair<Int, Int> ->
            listOf(
                c.first to c.second - 1,
                c.first to c.second + 1,
                c.first + 1 to c.second,
                c.first - 1 to c.second,
            ).filter { it.first in input.indices && it.second in input.first().indices }
        }

        var i = 0
        while (true) {
            i++
            beginnings = beginnings
                .flatMap { beginning -> next(beginning).filter { heightmap[it] - heightmap[beginning] <= 1 } }
                .distinct()

            if (beginnings.any { it == end }) {
                return i
            }
        }
    }

    fun part2(input: List<String>): Int {
        val heightmap = input.map { it.toCharArray().toTypedArray() }.toTypedArray()
        val flatten = heightmap.flatten()


        listOf(flatten.indexOf('S')
            .let { it / input.first().count() to it % input.first().count() }
            .also { heightmap[it] = 'a' }
        )
        val end = flatten.indexOf('E')
            .let { it / input.first().count() to it % input.first().count() }
            .also { heightmap[it] = 'z' }

        val next = { c: Pair<Int, Int> ->
            listOf(
                c.first to c.second - 1,
                c.first to c.second + 1,
                c.first + 1 to c.second,
                c.first - 1 to c.second,
            ).filter { it.first in input.indices && it.second in input.first().indices }
        }

        var beginnings = flatten
            .withIndex()
            .filter { it.value == 'a' }
            .map { (index, _) -> index / input.first().count() to index % input.first().count() }

        var i = 0
        while (true) {
            i++
            beginnings = beginnings
                .flatMap { beginning -> next(beginning).filter { heightmap[it] - heightmap[beginning] <= 1 } }
                .distinct()

            if (beginnings.any { it == end }) {
                return i
            }
        }
    }

    val inputTest = File("src/main/kotlin/day12_test.txt").readLines()
    val input = File("src/main/kotlin/day12.txt").readLines()

    check(part1(inputTest) == 31)
    println(part1(input))

    check(part2(inputTest) == 29)
    println(part2(input))
}