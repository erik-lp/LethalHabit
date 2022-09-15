package lethalhabit.game

import lethalhabit.display.GamePanel
import lethalhabit.hitbox.Hitbox
import lethalhabit.util.Point
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO

class Character(
    override val name: String,
    private val relativeHitbox: Hitbox,
    override val texture: Image,
) : Damageable, Named, Drawable {

    constructor(name: String, relativeHitbox: Hitbox, texturePath: String)
            : this(name, relativeHitbox, ImageIO.read(File(texturePath)).getScaledInstance(relativeHitbox.width, relativeHitbox.height, Image.SCALE_SMOOTH))

    override var position = Point(100, 100)
    override var isVisible = false
    override var isBypassable = false
    
    var hp: Int = 100
    var slam: Int = 100
    
    override val hitbox
        get() = relativeHitbox.translated(position)
    
    fun moveBy(x: Int, y: Int) {
        position = position.translated(x, y)
    }

    override fun damage(amount: Int) {
        hp -= amount
        if (hp <= 0) {
            die()
        }
    }
    
    override fun die() {
        hp = 0
        // TODO: death screen
    }

}