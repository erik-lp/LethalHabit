package lethalhabit.display

import lethalhabit.game.Character
import lethalhabit.game.CharacterController
import lethalhabit.hitbox.Hitbox
import lethalhabit.settings.GameRules.SHOW_HITBOXES
import lethalhabit.util.Point
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
        if (SHOW_HITBOXES) {
            g.drawPolygon(playerController.hitbox.points.map { it.x }.toIntArray(), playerController.hitbox.points.map { it.y }.toIntArray(), playerController.hitbox.points.size)
        }
    }
    
    override fun repaint() {
        playerController?.tick()
        super.repaint()
    }
    
}
