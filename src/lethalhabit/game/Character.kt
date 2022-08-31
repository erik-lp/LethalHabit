package lethalhabit.game

import lethalhabit.hitbox.Hitbox
import lethalhabit.util.Point
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO

class Character(
    override val name: String,
    relativeHitbox: Hitbox,
    override val texture: Image,
) : Hittable, Movable, Named, Drawable {

    constructor(position: Point, relativeHitbox: Hitbox, name: String, texturePath: String): this(name, relativeHitbox, ImageIO.read(File(texturePath)))

    override var position = Point(0, 0)
    override var isVisible = false
    override var isBypassable = false

    override val hitbox = relativeHitbox.translated(position)

    override fun tick() {

    }

    override fun onHit() {
        println("Wallah mach nicht diese")
    }

    override fun onJump() {
        println("Jumping")
    }

    override fun onMoveRight() {
        println("Moving right")
    }

    override fun onMoveLeft() {
        println("Moving left")
    }

    override fun onDash() {
        println("Dashing")
    }

}