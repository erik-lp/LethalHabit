package lethalhabit.util

data class Point(val x: Int, val y: Int) {
    fun translated(by: Point) = Point(x + by.x, y + by.y)
}
