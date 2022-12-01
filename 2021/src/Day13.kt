import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        val dots = input.filter { it.contains(',') }
            .map { it.split(",").let { it[0].toInt() to it[1].toInt() } }

        val folds = input.filter { it.contains("=") }
            .map {
                it.removePrefix("fold along ")
                    .split("=")
                    .let { it[0] to it[1].toInt() }
            }

        val fold = folds.first()

        return dots
            .map {
                when (fold.first) {
                    "x" -> {
                        if (it.first >= fold.second) {
                            fold.second * 2 - it.first
                        } else {
                            it.first
                        } to it.second
                    }
                    else -> {
                        it.first to if (it.second >= fold.second) {
                            fold.second * 2 - it.second
                        } else {
                            it.second
                        }
                    }
                }
            }
            .distinct()
            .count()
    }

    fun part2(input: List<String>): String {
        var dots = input.filter { it.contains(',') }
            .map { it.split(",").let { it[0].toInt() to it[1].toInt() } }

        val folds = input.filter { it.contains("=") }
            .map {
                it.removePrefix("fold along ")
                    .split("=")
                    .let { it[0] to it[1].toInt() }
            }

        folds.forEach { fold ->
            dots = dots
                .map {
                    when (fold.first) {
                        "x" -> {
                            if (it.first >= fold.second) {
                                (fold.second) * 2 - it.first
                            } else {
                                it.first
                            } to it.second
                        }
                        else -> {
                            it.first to if (it.second >= fold.second) {
                                (fold.second) * 2 - it.second
                            } else {
                                it.second
                            }
                        }
                    }
                }
                .distinct()
        }

        val code = Array(dots.maxOf { it.second } + 1) { CharArray(dots.maxOf { it.first } + 1) { '.' } }
        dots.forEach { code[it.second][it.first] = '#' }
        return code.joinToString("") { it.joinToString("") + "\n" }
    }

    val input = File("src", "Day13.txt").readLines()
    println(part1(input))
    println(part2(input))
}