import java.io.File
import java.util.*

fun main() {
    fun part1(input: List<String>): String {
        val stacksInput = input
            .takeWhile { it != "" }
            .dropLast(1)
            .reversed()
            .map { it.chunked(4).map { it[1] } }

        val procedureInput = input
            .drop(stacksInput.size + 2)
            .map { "(\\d)+".toRegex().findAll(it).map { it.groupValues[0].toInt() }.toList() }

        val stacks = List(stacksInput.maxOf { it.size }) { x ->
            Stack<Char>().apply {
                repeat(stacksInput.size) { y ->
                    stacksInput[y]
                        .getOrNull(x)
                        ?.takeIf { !it.isWhitespace() }
                        ?.let { this.push(it) }
                }
            }
        }

        procedureInput.forEach { (quantity, from, to) ->
            repeat(quantity) { stacks[to - 1].add(stacks[from - 1].pop()) }
        }

        return stacks.map { it.last() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val stacksInput = input
            .takeWhile { it != "" }
            .dropLast(1)
            .reversed()
            .map { it.chunked(4).map { it[1] } }

        val procedureInput = input
            .drop(stacksInput.size + 2)
            .map { "(\\d)+".toRegex().findAll(it).map { it.groupValues[0].toInt() }.toList() }

        val stacks = List(stacksInput.maxOf { it.size }) { x ->
            Stack<Char>().apply {
                repeat(stacksInput.size) { y ->
                    stacksInput[y]
                        .getOrNull(x)
                        ?.takeIf { !it.isWhitespace() }
                        ?.let { this.push(it) }
                }
            }
        }

        procedureInput.forEach { (quantity, from, to) ->
            stacks[to - 1].addAll(stacks[from - 1].takeLast(quantity))
            repeat(quantity) { stacks[from - 1].removeLast() }
        }

        return stacks.map { it.last() }.joinToString("")
    }

    val inputTest = File("src/main/kotlin/day05_test.txt").readLines()
    val input = File("src/main/kotlin/day05.txt").readLines()

    check(part1(inputTest) == "CMZ")
    println(part1(input))

    check(part2(inputTest) == "MCD")
    println(part2(input))
}