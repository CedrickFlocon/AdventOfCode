import java.io.File
import kotlin.time.measureTimedValue

fun resolve(dayNumber: Int, expected: String, block: (file: File) -> String): String {
    val d = String.format("%02d", dayNumber)

    val result = block(File("src/day${d}_test.txt"))
    check(result == expected) { "Check failed\nResult: $result\nExpected: $expected" }
    println("Check succeed")

    val (value, timeTaken) = measureTimedValue {
        block(File("src/day${d}.txt"))
    }
    println("Executed in : $timeTaken")

    return value
}
