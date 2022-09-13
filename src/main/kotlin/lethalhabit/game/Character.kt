package lethalhabit.game

import lethalhabit.hitbox.Hitbox
import lethalhabit.settings.moveRight
import lethalhabit.util.Direction
import lethalhabit.util.Point
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO

class Character(
    override val name: String,
    relativeHitbox: Hitbox,
    override val texture: Image,
) : Hittable, Named, Drawable {

    constructor(name: String, relativeHitbox: Hitbox, texturePath: String)
            : this(name, relativeHitbox, ImageIO.read(File(texturePath)).getScaledInstance(relativeHitbox.width, relativeHitbox.height, Image.SCALE_SMOOTH))

    override var position = Point(0, 0)
    override var isVisible = false
    override var isBypassable = false
    
    override val hitbox = relativeHitbox.translated(position)

    override fun tick() {

    }
    
    fun moveBy(x: Int, y: Int) {
        position = position.translated(x, y)
    }

    override fun onHit() {
        println("Wallah mach nicht diese")
    }

}