fun main() {
    day03PartOne()
    day03PartTwo()
}

fun day03PartOne() {
    resolve(3, "161") { file ->
        val regex = Regex("""mul\((\d+),(\d+)\)""")
        file
            .readLines()
            .flatMap { regex.findAll(it) }
            .map { it.groupValues[1].toLong() * it.groupValues[2].toLong() }
            .sum()
            .toString()
    }.also { println("Part one : $it") }
}

fun day03PartTwo() {
    resolve(3, "48") { file ->
        val regex = Regex("""mul\((\d+),(\d+)\)""")
        val doInstruction = Regex("""do\(\)""")
        val dontInstruction = Regex("""don't\(\)""")

        val input = file.readLines().joinToString("")

        val allDont = dontInstruction.findAll(input).map { it.range.first }.toHashSet()
        val allDo = doInstruction.findAll(input).map { it.range.first }.toHashSet()

        regex.findAll(input)
            .filter { operation ->
                val closestDont = allDont.filter { it < operation.range.first }.maxOrNull()
                    ?: return@filter true
                val closestDo = allDo.filter { it < operation.range.first }.maxOrNull()
                    ?: return@filter false

                closestDo > closestDont
            }
            .map { it.groupValues[1].toLong() * it.groupValues[2].toLong() }
            .sum()
            .toString()
    }.also { println("Part two : $it") }
}
