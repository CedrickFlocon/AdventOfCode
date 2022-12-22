import java.io.File

fun main() {

    val numberRegex = "(\\d+)".toRegex()

    fun check(left: String, right: String): Boolean {
        if (right.startsWith(']') && left.startsWith(']')) {
            return check(
                left = left.drop(1).trim(','),
                right = right.drop(1).trim(','),
            )
        }
        if (right.startsWith(']')) return false
        if (left.startsWith(']')) return true

        if (right.startsWith('[') && left.startsWith('[')) {
            return check(
                left = left.drop(1),
                right = right.drop(1)
            )
        }
        if (right.startsWith('[')) {
            val firstValue = numberRegex.find(left)?.value ?: return true
            return check(
                left = "[$firstValue]" + left.drop(firstValue.length),
                right = right,
            )
        }
        if (left.startsWith('[')) {
            val firstValue = numberRegex.find(right)?.value ?: return true
            return check(
                left = left,
                right = "[$firstValue]" + right.drop(firstValue.length),
            )
        }

        val rightFirstValue = numberRegex.find(right)?.value?.toInt() ?: return false
        val leftFirstValue = numberRegex.find(left)?.value?.toInt() ?: return true
        if (leftFirstValue < rightFirstValue) return true
        if (leftFirstValue > rightFirstValue) return false

        return check(
            left = left.dropWhile { it != ',' && it != ']' }.trim(','),
            right = right.dropWhile { it != ',' && it != ']' }.trim(',')
        )
    }

    fun part1(input: List<String>): Int {
        return input
            .chunked(3)
            .map { it[0] to it[1] }
            .withIndex()
            .map { it.copy(index = it.index + 1) }
            .filter { indexedValue ->
                check(
                    left = indexedValue.value.first,
                    right = indexedValue.value.second
                )
            }
            .sumOf { it.index }
    }

    fun part2(input: List<String>): Int {
        return (input + "[[2]]" + "[[6]]")
            .filter { it.isNotBlank() }
            .sortedWith { t1, t2 -> if (check(t1, t2)) -1 else 1 }
            .withIndex().map { it.copy(index = it.index + 1) }
            .onEach { println(it.value) }
            .filter { it.value == "[[2]]" || it.value == "[[6]]" }
            .fold(1) { acc, indexedValue -> indexedValue.index * acc }
    }

    val inputTest = File("src/main/kotlin/day13_test.txt").readLines()
    val input = File("src/main/kotlin/day13.txt").readLines()

    check(part1(inputTest) == 13)
    println(part1(input))

    check(part2(inputTest) == 140)
    println(part2(input))
}
