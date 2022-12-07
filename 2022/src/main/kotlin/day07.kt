import java.io.File
import java.lang.IllegalStateException

fun main() {
    class Node(
        val name: String,
        val parent: Node?,
        val child: MutableList<Node> = mutableListOf(),
        var size: Long = 0
    )

    tailrec fun updateSize(node: Node, size: Long) {
        node.size += size
        if (node.parent != null) updateSize(node.parent, size)
    }

    fun flatten(node: Node): List<Node> {
        return listOf(node) + node.child.flatMap { flatten(it) }
    }

    fun part1(input: List<String>): Long {
        val root = Node("/", null)
        var current = root

        input
            .drop(2)
            .filter { it != "$ ls" }
            .filter { !it.startsWith("dir") }
            .forEach {
                when {
                    it == "$ cd .." -> current = current.parent ?: throw IllegalStateException("cannot go up")
                    it.startsWith("$ cd") -> current.child.add(Node(it.split(" ")[2], current).also { current = it })
                    else -> updateSize(current, it.split(" ")[0].toLong())
                }
            }

        return flatten(root)
            .filter { it.size < 100000 }
            .sumOf { it.size }
    }

    fun part2(input: List<String>): Long {
        val root = Node("/", null)
        var current = root

        input
            .drop(2)
            .filter { it != "$ ls" }
            .filter { !it.startsWith("dir") }
            .forEach {
                when {
                    it == "$ cd .." -> current = current.parent ?: throw IllegalStateException("cannot go up")
                    it.startsWith("$ cd") -> current.child.add(Node(it.split(" ")[2], current).also { current = it })
                    else -> updateSize(current, it.split(" ")[0].toLong())
                }
            }

        return flatten(root)
            .filter { it.size > 30000000 - (70000000 - root.size) }
            .minBy { it.size }
            .size
    }

    val inputTest = File("src/main/kotlin/day07_test.txt").readLines()
    val input = File("src/main/kotlin/day07.txt").readLines()

    check(part1(inputTest) == 95437L)
    println(part1(input))

    check(part2(inputTest) == 24933642L)
    println(part2(input))
}
