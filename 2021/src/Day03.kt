import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        var gamma = ""
        var epsilon = ""

        for (i in input.first().indices) {
            val groupBy = input.map { it[i] }.joinToString("").groupBy { it }
            gamma += groupBy.maxByOrNull { it.value.size }?.key
            epsilon += groupBy.minByOrNull { it.value.size }?.key
        }

        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2)
    }

    fun part2(input: List<String>): Int {
        val o2 = input.toMutableList()
        val co2 = input.toMutableList()

        for (i in input.indices) {
            if (o2.size > 1) {
                o2.removeIf {
                    o2.map { it[i] }.joinToString("").groupBy { it }
                        .minByOrNull { it.value.size + it.key.code }
                        ?.key == it[i]
                }
            }

            if (co2.size > 1) {
                co2.removeIf {
                    co2.map { it[i] }.joinToString("").groupBy { it }
                        .maxByOrNull { it.value.size + it.key.code }
                        ?.key == it[i]
                }
            }
        }

        return Integer.parseInt(o2.first(), 2) * Integer.parseInt(co2.first(), 2)
    }

    val input = File("src", "Day03.txt").readLines()
    println(part1(input))
    println(part2(input))
}
