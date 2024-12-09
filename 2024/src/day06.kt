fun main() {
    day06PartOne()
    day06PartTwo()
}

fun day06PartOne() {
    resolve(6, "41") { file ->
        val computeCoordinate = listOf<(Pair<Int, Int>) -> Pair<Int, Int>>(
            { (x: Int, y: Int) -> x to y - 1 },
            { (x: Int, y: Int) -> x + 1 to y },
            { (x: Int, y: Int) -> x to y + 1 },
            { (x: Int, y: Int) -> x - 1 to y },
        )

        val lab = file.readLines().map { it.toCharArray() }.toTypedArray()

        val y = lab.indexOfFirst { it.contains('^') }
        val x = lab[y].indexOfFirst { it == '^' }
        var orientation = 0
        var coordinate = x to y

        game@ while (true) {
            orientation@ while (true) {
                lab[coordinate.second][coordinate.first] = 'X'
                val newCoordinate = computeCoordinate[orientation % 4](coordinate)
                val slot = lab.getOrNull(newCoordinate.second)?.getOrNull(newCoordinate.first) ?: break@game
                if (slot == '#') {
                    break@orientation
                } else {
                    coordinate = newCoordinate
                }
            }
            orientation++
        }

        lab.sumOf { it.count { it == 'X' } }.toString()
    }.also { println("Part one : $it") }
}

fun day06PartTwo() {
    resolve(6, "6") { file ->
        val computeCoordinate = listOf<(Pair<Int, Int>) -> Pair<Int, Int>>(
            { (x: Int, y: Int) -> x to y - 1 },
            { (x: Int, y: Int) -> x + 1 to y },
            { (x: Int, y: Int) -> x to y + 1 },
            { (x: Int, y: Int) -> x - 1 to y },
        )

        val lab = file.readLines().map { it.toCharArray() }.toTypedArray()
        val obstacle = mutableListOf<Pair<Int, Int>>()

        val y = lab.indexOfFirst { it.contains('^') }
        val x = lab[y].indexOfFirst { it == '^' }
        var orientation = 0
        var coordinate = x to y

        val isLoop = { originCoordinate: Pair<Int, Int>, fakeObstacle: Pair<Int, Int>, originOrientation: Int ->
            val loopPathCheck = Array<Array<MutableSet<Int>>>(lab.size) { Array(lab.size) { mutableSetOf() } }
            var c = originCoordinate
            var o = originOrientation
            var isLoop = false

            while (true) {
                loopPathCheck[c.second][c.first].add(o)
                val newCoordinate = computeCoordinate[o](c)
                val slot = lab.getOrNull(newCoordinate.second)?.getOrNull(newCoordinate.first) ?: break

                if (slot == '#' || newCoordinate == fakeObstacle) {
                    o = (o + 1) % 4
                    continue
                }

                c = newCoordinate
                if (loopPathCheck[newCoordinate.second][newCoordinate.first].any { it == o }) {
                    isLoop = true
                    break
                }
            }
            isLoop
        }

        while (true) {
            val newCoordinate = computeCoordinate[orientation](coordinate)
            val slot = lab.getOrNull(newCoordinate.second)?.getOrNull(newCoordinate.first) ?: break
            if (slot == '#') {
                orientation = (orientation + 1) % 4
                continue
            }

            if (slot != 'X' && isLoop(coordinate, newCoordinate, orientation)) {
                obstacle.add(newCoordinate)
            }

            coordinate = newCoordinate
            lab[coordinate.second][coordinate.first] = 'X'
        }

        obstacle.distinct().count().toString()
    }.also { println("Part two : $it") }
}
