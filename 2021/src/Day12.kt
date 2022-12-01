import java.io.File

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    fun part1(input: List<String>): Int {
        val map = mutableMapOf<String, MutableList<String>>()
        input.forEach {
            val (from, to) = it.split("-").let { it[0] to it[1] }
            map.getOrPut(from) { emptyList<String>().toMutableList() }.add(to)
            map.getOrPut(to) { emptyList<String>().toMutableList() }.add(from)
        }

        val paths = map["start"]!!.map { listOf("start", it) }.toMutableList()

        while (paths.any { it.last() != "end" }) {
            val path = paths.removeAt(paths.indexOfFirst { it.last() != "end" })

            map[path.last()]
                ?.filter { cave -> path.none { it == cave } || cave.uppercase() == cave }
                ?.forEach { paths.add(path + it) }
        }

        return paths.size
    }

    fun part2(input: List<String>): Int {
        val map = mutableMapOf<String, MutableList<String>>()
        input.forEach {
            val (from, to) = it.split("-").let { it[0] to it[1] }
            map.getOrPut(from) { emptyList<String>().toMutableList() }.add(to)
            map.getOrPut(to) { emptyList<String>().toMutableList() }.add(from)
        }

        var endedPath = 0
        val paths = map["start"]!!.map { listOf("start", it) }.toMutableList()

        while (paths.any { it.last() != "end" }) {
            val path = paths.removeAt(paths.indexOfFirst { it.last() != "end" })

            map[path.last()]
                ?.filter { it != "start" }
                ?.filter { cave -> cave.uppercase() == cave || path.none { it == cave } || path.filter { it.uppercase() != it }.groupBy { it }.maxOf { it.value.size } < 2 }
                ?.forEach {
                    if (it == "end") {
                        endedPath++
                    } else {
                        paths.add(path + it)
                    }
                }
        }

        return endedPath
    }

    val input = File("src", "Day12.txt").readLines()
    println(part1(input))
    println(part2(input))
}
