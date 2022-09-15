package lethalhabit.game

interface Damageable: Collidable {
    fun damage(amount: Int)
    fun die()
}
