package lethalhabit.util

import kotlin.math.max
import kotlin.math.min

class Edge(val point1: Point, val point2: Point) {

    val linearFunction = run {
        val slope = (point2.y - point1.y).toDouble() / (point2.x - point1.x).toDouble()
        val yIntercept = point1.y - slope * point1.x
        LinearFunction(slope, yIntercept)
    }

    val xMin get() = min(point1.x, point2.x).toDouble()
    val xMax get() = max(point1.x, point2.x).toDouble()
    val yMin get() = min(point1.y, point2.y).toDouble()
    val yMax get() = max(point1.y, point2.y).toDouble()

    fun intersects(other: Edge): Boolean {
        val intersectionX = (linearFunction.yIntercept - other.linearFunction.yIntercept) / (other.linearFunction.slope - linearFunction.slope)
        val intersectionY = linearFunction(intersectionX)
        return (intersectionX in xMin..xMax) && (intersectionX in other.xMin..other.xMax) && (intersectionY in yMin..yMax) && (intersectionY in other.yMin..other.yMax)
    }

    override fun toString(): String {
        return "Edge($point1, $point2, $linearFunction)"
    }

}
