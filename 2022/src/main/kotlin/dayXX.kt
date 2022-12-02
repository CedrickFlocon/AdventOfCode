import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val inputTest = File("src/main/kotlin/dayXX_test.txt").readLines()
    val input = File("src/main/kotlin/dayXX.txt").readLines()

    check(part1(inputTest) == 0)
    println(part1(input))

    check(part2(inputTest) == 0)
    println(part2(input))
}