import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        val scan = input.scan(1 to 0) { acc, op ->
            when (op[0]) {
                'a' -> acc.copy(first = acc.first + op.split(" ")[1].toInt(), second = acc.second + 2)
                'n' -> acc.copy(second = acc.second + 1)
                else -> throw IllegalArgumentException()
            }
        }

        return listOf(20, 60, 100, 140, 180, 220)
            .sumOf { step -> step * scan.last { it.second < step }.first }
    }

    fun part2(input: List<String>): String {
        return input
            .scan(listOf(0)) { acc, op ->
                val last = acc.last()
                when (op[0]) {
                    'a' -> listOf(last, last + op.split(" ")[1].toInt())
                    'n' -> listOf(last)
                    else -> throw IllegalArgumentException()
                }
            }
            .flatten()
            .mapIndexed { index, x ->
                val lineIndex = index % 40
                if (lineIndex in x until (x + 3)) '#' else '.'
            }
            .joinToString("")
            .chunked(40)
            .dropLast(1)
            .joinToString("\n")
    }

    val inputTest = File("src/main/kotlin/day10_test.txt").readLines()
    val input = File("src/main/kotlin/day10.txt").readLines()

    check(part1(inputTest) == 13140)
    println(part1(input))

    check(
        part2(inputTest) ==
                """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
                """.trimIndent()
    )
    println(part2(input))
}