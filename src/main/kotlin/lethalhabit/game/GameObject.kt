package lethalhabit.game

import lethalhabit.util.Point

interface GameObject {
    var position: Point

    fun tick()
}
