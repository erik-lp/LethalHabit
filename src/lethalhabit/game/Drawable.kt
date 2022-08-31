package lethalhabit.game

import java.awt.Image

interface Drawable: GameObject {
    var isVisible: Boolean
    val texture: Image
}
