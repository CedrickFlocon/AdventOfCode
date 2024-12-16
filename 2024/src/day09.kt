import java.util.*
import kotlin.math.min

fun main() {
    day09PartOne()
    day09PartTwo()
}

fun day09PartOne() {
    resolve(9, "1928") { file ->
        val disk = LinkedList(
            file
                .readLines().first()
                .withIndex()
                .map { it.index to it.value.digitToInt() })
        val unwrapDisk = mutableListOf<Int>()

        unwrap@ while (true) {
            val space = disk.poll() ?: break@unwrap
            if (space.first % 2 == 0) {
                unwrapDisk.addAll(List(space.second) { space.first / 2 })
            } else {
                while (true) {
                    val last = disk.pollLast() ?: break@unwrap
                    if (last.first % 2 == 1) continue
                    val usedSpace = min(space.second, last.second)
                    unwrapDisk.addAll(List(usedSpace) { last.first / 2 })

                    if (last.second > space.second) {
                        disk.add(last.first to last.second - usedSpace)
                    } else if (last.second < space.second) {
                        disk.addFirst(space.first to space.second - usedSpace)
                    }
                    break
                }
            }
        }
        unwrapDisk
            .withIndex()
            .sumOf { it.index * it.value.toLong() }
            .toString()
    }.also { println("Part one : $it") }
}

fun day09PartTwo() {
    resolve(9, "2858") { file ->
        val unwrapDisk =
            file
                .readLines().first()
                .withIndex()
                .map { indexValue -> List(indexValue.value.digitToInt()) { indexValue.index.takeIf { it % 2 == 0 }?.div(2) }.toMutableList() }
                .filter { it.isNotEmpty() }
                .toMutableList()

        for (diskFile in unwrapDisk.filter { it.any { it != null } }.reversed()) {
            val index = unwrapDisk.indexOfFirst { it.any { it == diskFile.first() } }

            val emptyIndex = unwrapDisk
                .withIndex()
                .indexOfFirst { it.index < index && it.value.first() == null && it.value.size >= diskFile.size }
                .takeIf { it >= 0 } ?: continue

            val missingSize = unwrapDisk[emptyIndex].size - diskFile.size
            unwrapDisk[emptyIndex] = diskFile
            unwrapDisk[index] = List(diskFile.size) { null }.toMutableList()
            if (missingSize > 0) {
                unwrapDisk.add(emptyIndex + 1, List(missingSize) { null }.toMutableList())
            }
        }

        unwrapDisk
            .flatten()
            .withIndex()
            .filter { it.value != null }
            .sumOf { it.index * it.value!!.toLong() }
            .toString()
    }.also { println("Part two : $it") }
}
