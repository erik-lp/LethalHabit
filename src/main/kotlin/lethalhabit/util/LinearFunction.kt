package lethalhabit.util

class LinearFunction(val slope: Double, val yIntercept: Double): (Double) -> Double {
    override operator fun invoke(x: Double): Double {
        return slope * x + yIntercept
    }

    override fun toString(): String {
        return "${slope}x + $yIntercept"
    }
}
