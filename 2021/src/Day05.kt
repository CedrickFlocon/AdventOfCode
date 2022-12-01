import java.awt.Point
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

fun main() {
    fun part1(vents: List<Pair<Point, Point>>): Int {
        val map = Array(1000) { Array(1000) { 0 } }
        var overlapNumber = 0

        vents.filter { it.first.x == it.second.x || it.first.y == it.second.y }
            .forEach { vent ->
                (min(vent.first.x, vent.second.x)..max(vent.first.x, vent.second.x)).forEach { x ->
                    (min(vent.first.y, vent.second.y)..max(vent.first.y, vent.second.y)).forEach { y ->
                        if (++map[x][y] == 2) {
                            overlapNumber++
                        }
                    }
                }
            }

        return overlapNumber
    }

    fun part2(vents: List<Pair<Point, Point>>): Int {
        val map = Array(1000) { Array(1000) { 0 } }
        var overlapNumber = 0

        vents.forEach { vent ->
            val scalar = Point(vent.second.x - vent.first.x, vent.second.y - vent.first.y)

            (0..max(scalar.x.absoluteValue, scalar.y.absoluteValue)).forEach {
                val x = vent.first.x + min(scalar.x.absoluteValue, if (scalar.x.sign == -1) it.unaryMinus() else it)
                val y = vent.first.y + min(scalar.y.absoluteValue, if (scalar.y.sign == -1) it.unaryMinus() else it)
                if (++map[x][y] == 2) {
                    overlapNumber++
                }
            }
        }

        return overlapNumber
    }

    val input = File("src", "Day05.txt").readLines()

    val vents = input.map {
        it.split("->").map { it.trim() }.map { it.split(",").let { Point(it[0].toInt(), it[1].toInt()) } }
    }.map { it[0] to it[1] }

    println(part1(vents))
    println(part2(vents))
}
