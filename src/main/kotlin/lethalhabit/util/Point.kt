package lethalhabit.util

data class Point(val x: Int, val y: Int) {
    fun translated(by: Point) = translated(by.x, by.y)
    fun translated(x: Int, y: Int) = Point(this.x + x, this.y + y)
}
