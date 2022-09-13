package lethalhabit.hitbox

import lethalhabit.util.Point
import lethalhabit.util.Edge
import lethalhabit.util.*

class Hitbox(val points: List<Point>) {

    val edges: List<Edge> = points.mapIndexed { i, corner ->
        val nextCorner = points.getOrNull(i + 1) ?: points[0]
        Edge(corner, nextCorner)
    }

    val xMax = points.maxOf { it.x }
    val xMin = points.minOf { it.x }
    val yMax = points.maxOf { it.y }
    val yMin = points.minOf { it.y }

    val width = xMax - xMin
    val height = yMax - yMin

    fun collides(other: Hitbox): Boolean {
        for (point in points) {
            if (other.containsPoint(point)) return true
        }
        for (point in other.points) {
            if (containsPoint(point)) return true
        }
        for (edge1 in edges) {
            for (edge2 in other.edges) {
                if (edge1.intersects(edge2)) return true
            }
        }
        return false
    }

    fun containsPoint(point: Point): Boolean {
        val x = point.x
        val y = point.y
        val alreadyIntersectedLeft = arrayListOf<Point>()
        val alreadyIntersectedRight = arrayListOf<Point>()
        val alreadyIntersectedUp = arrayListOf<Point>()
        val alreadyIntersectedDown = arrayListOf<Point>()
        fun checkLeft(edge: Edge): Boolean {
            if (edge.linearFunction.slope == 0.0) {
                if (edge.yMin == y.toDouble()) {
                    if (edge.xMin <= x && edge.xMax <= x) {
                        alreadyIntersectedLeft.add(Point(edge.xMin.toInt(), y))
                        alreadyIntersectedLeft.add(Point(edge.xMax.toInt(), y))
                        return true
                    } else if (edge.xMax <= x) {
                        alreadyIntersectedLeft.add(Point(edge.xMax.toInt(), y))
                        return true
                    } else if (edge.xMin <= x) {
                        alreadyIntersectedLeft.add(Point(edge.xMin.toInt(), y))
                        return true
                    }
                }
            } else if (y.toDouble() in edge.yMin..edge.yMax) {
                if (edge.linearFunction.slope.isInfinite() && edge.xMin <= x) {
                    alreadyIntersectedLeft.add(Point(edge.xMin.toInt(), y))
                    return true
                }
                val intersection = (y.toDouble() - edge.linearFunction.yIntercept) / edge.linearFunction.slope
                val intersectionPoint = Point(intersection.toInt(), y)
                if (intersection <= x && (intersection in edge.xMin..edge.xMax) && intersectionPoint !in alreadyIntersectedLeft) {
                    alreadyIntersectedLeft.add(intersectionPoint)
                    return true
                }
            }
            return false
        }
        fun checkRight(edge: Edge): Boolean {
            if (edge.linearFunction.slope == 0.0) {
                if (edge.yMin == y.toDouble()) {
                    if (edge.xMin >= x && edge.xMax >= x) {
                        alreadyIntersectedRight.add(Point(edge.xMin.toInt(), y))
                        alreadyIntersectedRight.add(Point(edge.xMax.toInt(), y))
                        return true
                    } else if (edge.xMax >= x) {
                        alreadyIntersectedRight.add(Point(edge.xMax.toInt(), y))
                        return true
                    } else if (edge.xMin >= x) {
                        alreadyIntersectedRight.add(Point(edge.xMin.toInt(), y))
                        return true
                    }
                }
            } else if (y.toDouble() in edge.yMin..edge.yMax) {
                if (edge.linearFunction.slope.isInfinite() && edge.xMin >= x) {
                    alreadyIntersectedRight.add(Point(edge.xMin.toInt(), y))
                    return true
                }
                val intersection = if (edge.linearFunction.slope.isInfinite()) edge.xMin else (y.toDouble() - edge.linearFunction.yIntercept) / edge.linearFunction.slope
                val intersectionPoint = Point(intersection.toInt(), y)
                if (intersection >= x && (intersection in edge.xMin..edge.xMax) && intersectionPoint !in alreadyIntersectedRight) {
                    alreadyIntersectedRight.add(intersectionPoint)
                    return true
                }
            }
            return false
        }
        fun checkUp(edge: Edge): Boolean {
            if (edge.linearFunction.slope.isInfinite()) {
                if (edge.xMin == x.toDouble()) {
                    if (edge.yMin >= y && edge.yMax >= y) {
                        alreadyIntersectedUp.add(Point(x, edge.yMin.toInt()))
                        alreadyIntersectedUp.add(Point(x, edge.yMax.toInt()))
                        return true
                    } else if (edge.yMax >= y) {
                        alreadyIntersectedUp.add(Point(x, edge.yMax.toInt()))
                        return true
                    } else if (edge.yMin >= y) {
                        alreadyIntersectedUp.add(Point(x, edge.yMin.toInt()))
                        return true
                    }
                }
            } else if (x.toDouble() in edge.xMin..edge.xMax) {
                if (edge.linearFunction.slope == 0.0 && edge.yMin >= y) {
                    alreadyIntersectedUp.add(Point(x, edge.yMin.toInt()))
                    return true
                }
                val intersection = edge.linearFunction(x.toDouble())
                val intersectionPoint = Point(x, intersection.toInt())
                if (intersection >= y && (intersection in edge.yMin..edge.yMax) && intersectionPoint !in alreadyIntersectedUp) {
                    alreadyIntersectedUp.add(intersectionPoint)
                    return true
                }
            }
            return false
        }
        fun checkDown(edge: Edge): Boolean {
            if (edge.linearFunction.slope.isInfinite()) {
                if (edge.xMin == x.toDouble()) {
                    if (edge.yMin <= y && edge.yMax <= y) {
                        alreadyIntersectedUp.add(Point(x, edge.yMin.toInt()))
                        alreadyIntersectedUp.add(Point(x, edge.yMax.toInt()))
                        return true
                    } else if (edge.yMax <= y) {
                        alreadyIntersectedUp.add(Point(x, edge.yMax.toInt()))
                        return true
                    } else if (edge.yMin <= y) {
                        alreadyIntersectedUp.add(Point(x, edge.yMin.toInt()))
                        return true
                    }
                }
            } else if (x.toDouble() in edge.xMin..edge.xMax) {
                if (edge.linearFunction.slope == 0.0 && edge.yMin <= y) {
                    alreadyIntersectedUp.add(Point(x, edge.yMin.toInt()))
                    return true
                }
                val intersection = edge.linearFunction(x.toDouble())
                val intersectionPoint = Point(x, intersection.toInt())
                if (intersection <= y && (intersection in edge.yMin..edge.yMax) && intersectionPoint !in alreadyIntersectedDown) {
                    alreadyIntersectedDown.add(intersectionPoint)
                    return true
                }
            }
            return false
        }
        var left = 0
        var right = 0
        var up = 0
        var down = 0
        for (edge in edges) {
            if (checkLeft(edge)) left++
            if (checkRight(edge)) right++
            if (checkUp(edge)) up++
            if (checkDown(edge)) down++
        }
        return left.isOdd && right.isOdd && up.isOdd && down.isOdd
    }

    fun translated(by: Point) = Hitbox(points.map { it.translated(by) })

}
