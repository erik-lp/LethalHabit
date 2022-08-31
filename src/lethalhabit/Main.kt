package lethalhabit

import lethalhabit.hitbox.Hitbox
import lethalhabit.util.*
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Panel
import javax.swing.JFrame
import kotlin.random.Random

val X = 1000
val Y = 700

fun main() {
    val window = JFrame("Hitbox test")
    val hitbox1 = randomHitbox()
    val hitbox2 = randomHitbox()
    window.size  = Dimension(X, Y)
    window.contentPane = HitboxTestPanel(hitbox1, hitbox2)
    window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    window.isVisible = true
    println("Time passed (ms): ${measureTime {
        val isCollision = hitbox1.collides(hitbox2)
        println("Hitboxes collide: $isCollision")
    }}")
    println("Time for updating (ms): ${measureTime { 
        window.contentPane.update(window.graphics) 
    }}")
}

inline fun measureTime(action: () -> Unit): Double {
    val now = System.nanoTime()
    action()
    val later = System.nanoTime()
    return (later - now).toDouble() / 1000000
}

fun randomHitbox(): Hitbox {
    val vertices = Random.nextInt(3, 8)
    val points = Array(vertices) { Point(Random.nextInt(X), Random.nextInt(Y)) }
    return Hitbox(points.toList())
}

class HitboxTestPanel(val hitbox1: Hitbox, val hitbox2: Hitbox) : Panel() {
    override fun paint(g: Graphics) {
        g.color = Color.BLUE
        for ((i, point) in hitbox1.points.withIndex()) {
            val (x2, y2) = hitbox1.points.getOrNull(i + 1) ?: hitbox1.points[0]
            g.drawLine(point.x, point.y, x2, y2)
        }
        g.color = Color.RED
        for ((i, point) in hitbox2.points.withIndex()) {
            val (x2, y2) = hitbox2.points.getOrNull(i + 1) ?: hitbox2.points[0]
            g.drawLine(point.x, point.y, x2, y2)
        }
    }
}
