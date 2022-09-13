package lethalhabit.util

inline fun measureTime(action: () -> Unit): Double {
    val now = System.nanoTime()
    action()
    val later = System.nanoTime()
    return (later - now).toDouble() / 1000000
}
