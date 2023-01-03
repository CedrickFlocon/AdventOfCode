import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>, y: Int): Int {
        val number = "(-?\\d+)".toRegex()

        val distance = { a: Pair<Int, Int>, b: Pair<Int, Int> ->
            abs((a.first - b.first)) + abs((a.second - b.second))
        }

        val scan = input.map {
            val coordinates = number.findAll(it).map { it.value.toInt() }.toList()
            val sensor = coordinates[0] to coordinates[1]
            val beacon = coordinates[2] to coordinates[3]
            sensor to beacon
        }

        val distanceMax = scan.maxOf { (sensor, beacon) -> distance(sensor, beacon) }
        val minX = scan.minOf { (sensor, beacon) -> min(sensor.first, (beacon.first)) } - distanceMax
        val maxX = scan.maxOf { (sensor, beacon) -> max(sensor.first, (beacon.first)) } + distanceMax

        return (minX until maxX).count { x ->
            scan.any { (sensor, beacon) ->
                distance(sensor, x to y) <= distance(sensor, beacon) && beacon != x to y
            }
        }
    }

    fun part2(input: List<String>, max: Int): Long {
        val number = "(-?\\d+)".toRegex()

        val distance = { a: Pair<Int, Int>, b: Pair<Int, Int> ->
            abs((a.first - b.first)) + abs((a.second - b.second))
        }

        val scan = input.map {
            val coordinates = number.findAll(it).map { it.value.toInt() }.toList()
            val sensor = coordinates[0] to coordinates[1]
            val beacon = coordinates[2] to coordinates[3]
            Triple(sensor, beacon, distance(sensor, beacon))
        }

        for (y in (0 until max)) {
            var x = 0
            do {
                val zone = scan
                    .filter { (sensor, _, distance) -> distance(sensor, x to y) <= distance }
                    .minByOrNull { (sensor, _, distance) -> distance - distance(sensor, x to y) }
                    ?: return x.toLong() * 4000000L + y.toLong()

                x = zone.first.first + zone.third - abs(zone.first.second - y) + 1
            } while (x <= max)
        }

        throw IllegalStateException()
    }

    val inputTest = File("src/main/kotlin/day15_test.txt").readLines()
    val input = File("src/main/kotlin/day15.txt").readLines()

    check(part1(inputTest, 10) == 26)
    println(part1(input, 2000000))

    check(part2(inputTest, 20) == 56000011L)
    println(part2(input, 4000000))
}