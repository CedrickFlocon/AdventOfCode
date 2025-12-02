fun main() {
    day12PartOne()
    day12PartTwo()
}

fun day12PartOne() {
    resolve(12, "1930") { file ->
        val side = listOf<(Pair<Int, Int>) -> Pair<Int, Int>>(
            { (i, j) -> i to j + 1 },
            { (i, j) -> i to j - 1 },
            { (i, j) -> i - 1 to j },
            { (i, j) -> i + 1 to j }
        )

        val garden = file
            .readLines()
            .withIndex()
            .flatMap { (i, line) -> line.withIndex().map { Triple(i, it.index, it.value) } }
            .groupBy({ it.third }, { it.first to it.second })

        garden.values
            .flatMap { coordinates ->
                val zones = mutableListOf<Set<Pair<Int, Int>>>()
                while (zones.sumOf { it.size } != coordinates.size) {
                    val zone = mutableSetOf(coordinates.first { coordinate -> zones.none { it.any { it == coordinate } } })
                    val buildingZone = ArrayDeque(zone)

                    do {
                        val ground = buildingZone.removeLast()
                        val neighbors = side.map { it.invoke(ground) }.filter { it in coordinates && it !in zone }
                        zone.addAll(neighbors)
                        buildingZone.addAll(neighbors)
                    } while (buildingZone.isNotEmpty())

                    zones.add(zone)
                }
                zones
            }.map { coordinates ->
                coordinates.fold(0) { acc, coordinate ->
                    acc + side.map { it.invoke(coordinate) }.filter { it !in coordinates }.size
                } * coordinates.size
            }.sum().toString()
    }.also { println("Part one : $it") }
}

fun day12PartTwo() {
    resolve(12, "1206") { file ->
        val sides = mapOf<Side, (Pair<Int, Int>) -> Pair<Int, Int>>(
            Side.RIGHT to { (i, j) -> i to j + 1 },
            Side.LEFT to { (i, j) -> i to j - 1 },
            Side.TOP to { (i, j) -> i - 1 to j },
            Side.BOTTOM to { (i, j) -> i + 1 to j }
        )

        val garden = file
            .readLines()
            .withIndex()
            .flatMap { (i, line) -> line.withIndex().map { Triple(i, it.index, it.value) } }
            .groupBy({ it.third }, { it.first to it.second })

        garden.values
            .flatMap { coordinates ->
                val zones = mutableListOf<Set<Pair<Int, Int>>>()
                while (zones.sumOf { it.size } != coordinates.size) {
                    val zone = mutableSetOf(coordinates.first { coordinate -> zones.none { it.any { it == coordinate } } })
                    val buildingZone = ArrayDeque(zone)

                    do {
                        val ground = buildingZone.removeLast()
                        val neighbors = sides.map { it.value.invoke(ground) }.filter { it in coordinates && it !in zone }
                        zone.addAll(neighbors)
                        buildingZone.addAll(neighbors)
                    } while (buildingZone.isNotEmpty())

                    zones.add(zone)
                }
                zones
            }
            .map { coordinates ->
                sides
                    .flatMap { side -> coordinates.filter { side.value(it) !in coordinates }.map { side.key to it } }
                    .groupBy({ it.first }, { it.second })
                    .map {
                        val sideGroup = when (it.key) {
                            Side.LEFT, Side.RIGHT -> it.value.groupBy({ it.second }, { it.first })
                            Side.TOP, Side.BOTTOM -> it.value.groupBy({ it.first }, { it.second })
                        }.mapValues { it.value.sorted() }

                        (sideGroup.values.map { it.windowed(2).count { (first, second) -> first + 1 != second } }.sum() + sideGroup.size) * coordinates.size
                    }
                    .sum()
            }
            .sum()
            .toString()
    }.also { println("Part two : $it") }
}

enum class Side { LEFT, RIGHT, TOP, BOTTOM }
