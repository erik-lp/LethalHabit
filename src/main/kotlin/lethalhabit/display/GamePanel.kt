package lethalhabit.display

import lethalhabit.game.Character
import lethalhabit.game.CharacterController
import lethalhabit.hitbox.Hitbox
import lethalhabit.settings.GameRules.SHOW_HITBOXES
import lethalhabit.util.Point
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

object GamePanel : JPanel() {
    
    val playerController = CharacterController(
        Character(
            "Hollomat Holgerson", Hitbox(listOf(Point(0, 0), Point(0, 100), Point(170, 100), Point(170, 0))), "C:\\Users\\erik-\\Pictures\\electric-guitar-hand-drawing-type-sg-not-real-type-35496978.jpg"
        )
    )
    
    override fun paint(g: Graphics) {
        super.paint(g)
        g.drawImage(playerController.texture, playerController.position.x, playerController.position.y, ::imageUpdate)
        drawHealthBar(g)
        drawSlamBar(g)
        if (SHOW_HITBOXES) {
            g.drawPolygon(playerController.hitbox.points.map { it.x }.toIntArray(), playerController.hitbox.points.map { it.y }.toIntArray(), playerController.hitbox.points.size)
        }
    }
    
    override fun repaint() {
        playerController?.tick()
        super.repaint()
    }
    
    private fun drawHealthBar(g: Graphics) {
        g.drawPolygon(intArrayOf(19, 120, 120, 19), intArrayOf(19, 19, 35, 35), 4)
        g.color = Color.RED
        g.fillPolygon(intArrayOf(20, 20 + playerController.hp, 20 + playerController.hp, 20), intArrayOf(20, 20, 35, 35), 4)
        g.color = Color.LIGHT_GRAY
        g.fillPolygon(intArrayOf(20 + playerController.hp, 120, 120, 20 + playerController.hp), intArrayOf(20, 20, 35, 35), 4)
        g.color = Color.BLACK
    }
    
    private fun drawSlamBar(g: Graphics) {
        g.drawPolygon(intArrayOf(19, 120, 120, 19), intArrayOf(44, 44, 60, 60), 4)
        g.color = Color.CYAN
        g.fillPolygon(intArrayOf(20, 20 + playerController.slam, 20 + playerController.slam, 20), intArrayOf(45, 45, 60, 60), 4)
        g.color = Color.LIGHT_GRAY
        g.fillPolygon(intArrayOf(20 + playerController.slam, 120, 120, 20 + playerController.slam), intArrayOf(45, 45, 60, 60), 4)
        g.color = Color.BLACK
    }
    
}
