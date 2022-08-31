package lethalhabit.game

import lethalhabit.hitbox.Hitbox

interface Collidable: GameObject {
    var isBypassable: Boolean
    val hitbox: Hitbox
}
