package lethalhabit

import lethalhabit.display.GamePanel
import java.awt.Dimension
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.swing.JFrame

const val X = 1000
const val Y = 700

fun main() {
    val window = JFrame("Hitbox test")
    window.size  = Dimension(X, Y)
    window.contentPane = GamePanel
    window.addKeyListener(GamePanel.playerController)
    window.addMouseListener(GamePanel.playerController)
    window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    window.isVisible = true
    val action = {
        window.contentPane.repaint()
    }
    val executor = Executors.newScheduledThreadPool(1)
    executor.scheduleAtFixedRate(action, 0, 25, TimeUnit.MILLISECONDS)
}
