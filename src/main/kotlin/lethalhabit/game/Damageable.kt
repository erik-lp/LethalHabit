package lethalhabit.game

interface Damageable: Collidable {
    fun damage(amount: Int)
    fun heal(amount: Int)
    fun die()
}
