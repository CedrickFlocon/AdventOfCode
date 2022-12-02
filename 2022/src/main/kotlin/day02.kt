import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            it.split(' ')
                .map { Shape.fromChar(it.first()) }
        }.sumOf { it[1].score() + it[1].play(it[0]).score() }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            it.split(' ').map { it.first() }.let { Shape.fromChar(it[0]) to Result.fromChar(it[1]) }
        }.sumOf { it.second.score() + it.first.result(it.second).score() }
    }

    val inputTest = File("src/main/kotlin/day02_test.txt").readLines()
    val input = File("src/main/kotlin/day02.txt").readLines()

    check(part1(inputTest) == 15)
    println(part1(input))

    check(part2(inputTest) == 12)
    println(part2(input))
}


sealed interface Shape {

    companion object {
        fun fromChar(char: Char) = when (char) {
            'A', 'X' -> Rock
            'B', 'Y' -> Paper
            'C', 'Z' -> Scissors
            else -> throw IllegalArgumentException()
        }
    }

    fun result(result: Result): Shape
    fun play(shape: Shape): Result
    fun score(): Int


    object Rock : Shape {

        override fun result(result: Result) = when (result) {
            Result.Draw -> this
            Result.Lose -> Scissors
            Result.Win -> Paper
        }


        override fun play(shape: Shape) = when (shape) {
            Rock -> Result.Draw
            Paper -> Result.Lose
            Scissors -> Result.Win
        }

        override fun score() = 1

    }

    object Paper : Shape {

        override fun result(result: Result) = when (result) {
            Result.Draw -> this
            Result.Lose -> Rock
            Result.Win -> Scissors
        }

        override fun play(shape: Shape) = when (shape) {
            Paper -> Result.Draw
            Scissors -> Result.Lose
            Rock -> Result.Win
        }

        override fun score() = 2
    }

    object Scissors : Shape {

        override fun result(result: Result) = when (result) {
            Result.Draw -> this
            Result.Lose -> Paper
            Result.Win -> Rock
        }

        override fun play(shape: Shape) = when (shape) {
            Scissors -> Result.Draw
            Rock -> Result.Lose
            Paper -> Result.Win
        }

        override fun score() = 3
    }

}

sealed interface Result {

    companion object {
        fun fromChar(char: Char) = when (char) {
            'X' -> Lose
            'Y' -> Draw
            'Z' -> Win
            else -> throw IllegalArgumentException()
        }
    }

    fun score(): Int

    object Lose : Result {
        override fun score() = 0
    }

    object Draw : Result {
        override fun score() = 3
    }

    object Win : Result {
        override fun score() = 6
    }

}
