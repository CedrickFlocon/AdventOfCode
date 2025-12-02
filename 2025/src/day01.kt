import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    day01PartOne()
    day01PartTwo()
}

fun day01PartOne() {
    resolve(1, "3") { file ->
        file.readLines()
            .scan(50) { acc, rotation ->
                val isRight = rotation.first() == 'R'
                val number = rotation.drop(1).toInt() % 100

                val operation = when (isRight) {
                    true -> acc + number
                    false -> acc - number
                }

                when {
                    operation >= 100 -> operation - 100
                    operation < 0 -> operation + 100
                    else -> operation
                }
            }.count { it == 0 }.toString()
    }.also { println("Part one : $it") }
}

fun day01PartTwo() {
    resolve(1, "6") { file ->
        var count = 0

        file.readLines()
            .scan(50) { acc, rotation ->
                val isRight = rotation.first() == 'R'
                val number = rotation.drop(1).toInt()

                val operation = when (isRight) {
                    true -> acc + number
                    false -> acc - number
                }

                if (acc != 0 && acc.sign != operation.sign) {
                    count++
                }

                count += operation.absoluteValue / 100

                val modulo = operation % 100

                when {
                    modulo < 0 -> modulo + 100
                    else -> modulo
                }
            }
        count.toString()
    }.also { println("Part two : $it") }
}


//2167 too low
