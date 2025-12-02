fun main() {
    dayXXPartOne()
    dayXXPartTwo()
}

fun dayXXPartOne() {
    resolve(0, "Hello world") { file ->
        "Hello world"
    }.also { println("Part one : $it") }
}

fun dayXXPartTwo() {
    resolve(0, "Hello world") { file ->
        "Hello world"
    }.also { println("Part two : $it") }
}
