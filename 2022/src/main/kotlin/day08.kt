import java.io.File
import kotlin.math.max

fun main() {
    fun <T> List<T>.takeWhileInclusive(predicate: (T) -> Boolean): List<T> {
        val takeWhile = takeWhile(predicate)
        return takeWhile.plus(drop(takeWhile.size).firstOrNull()).mapNotNull { it }
    }

    fun part1(input: List<String>): Int {
        val treeMap = input.map { it.map { it.digitToInt() } }
        val visible = Array(treeMap.size) { Array(treeMap.size) { false } }

        val operation: (max: Int, Pair<Int, Int>) -> Int = { max, (x, y) ->
            visible[x][y] = visible[x][y] || max < treeMap[x][y]
            max(max, treeMap[x][y])
        }

        treeMap.indices.forEach { i ->
            (treeMap.indices).map { i to it }.fold(-1, operation)
            (treeMap.indices).reversed().map { i to it }.fold(-1, operation)
            (treeMap.indices).map { it to i }.fold(-1, operation)
            (treeMap.indices).reversed().map { it to i }.fold(-1, operation)
        }
        return visible.flatten().count { it }
    }

    fun part2(input: List<String>): Int {
        val treeMap = input.map { it.map { it.digitToInt() } }
        val visible = Array(treeMap.size) { Array(treeMap.size) { 0 } }

        treeMap.indices.forEach { x ->
            treeMap.indices.forEach { y ->
                val size = treeMap[x][y]
                val up = (x downTo 0).filter { it != x }.takeWhileInclusive { treeMap[it][y] < size }.count()
                val down = (x until treeMap.size).filter { it != x }.takeWhileInclusive { treeMap[it][y] < size }.count()
                val left = (y downTo 0).filter { it != y }.takeWhileInclusive { treeMap[x][it] < size }.count()
                val right = (y until treeMap.size).filter { it != y }.takeWhileInclusive { treeMap[x][it] < size }.count()
                visible[x][y] = up * down * left * right
            }
        }
        return visible.flatten().max()
    }

    val inputTest = File("src/main/kotlin/day08_test.txt").readLines()
    val input = File("src/main/kotlin/day08.txt").readLines()

    check(part1(inputTest) == 21)
    println(part1(input))

    check(part2(inputTest) == 8)
    println(part2(input))
}