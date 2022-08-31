package lethalhabit.display

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Panel
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.swing.JFrame

fun main() {
    val window = JFrame("Display Test")
    window.size = Dimension(1000, 700)
    window.contentPane = TestPanel()
    window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    window.isVisible = true
    val action = {
        window.contentPane.repaint()
    }
    val executor = Executors.newScheduledThreadPool(1)
    executor.scheduleAtFixedRate(action, 0, 25, TimeUnit.MILLISECONDS)
}

class TestPanel : Panel() {
    private var xNew = 0
    override fun repaint() {
        xNew++
        super.repaint()
    }
    override fun paint(g: Graphics) {
        g.color = Color.RED
        g.drawRect(xNew, 0,  100, 100)
    }
}
