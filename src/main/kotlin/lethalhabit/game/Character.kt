package lethalhabit.game

import lethalhabit.display.GamePanel
import lethalhabit.hitbox.Hitbox
import lethalhabit.util.Point
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.max
import kotlin.math.min

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
    
    var ability1: Ability? = Ability("Ability 1", 50, 20) { println("Ability 1 used") }
    var ability2: Ability? = Ability("Ability 2", 60, 30) { println("Ability 2 used") }
    var ability3: Ability? = Ability("Ability 3", 70, 40) { println("Ability 3 used") }
    var ability4: Ability? = Ability("Ability 4", 100, 100) { println("Ability 4 used") }
    
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
    
    override fun heal(amount: Int) {
        hp = min(hp + amount, 100)
    }
    
    fun useSlam(amount: Int) {
        slam = max(slam - amount, 0)
    }
    
    fun regenerateSlam(amount: Int) {
        slam = min(slam + amount, 100)
    }
    
    override fun die() {
        hp = 0
        // TODO: death screen
    }

}