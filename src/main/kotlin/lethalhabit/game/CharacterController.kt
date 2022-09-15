package lethalhabit.game

import lethalhabit.hitbox.Hitbox
import lethalhabit.settings.*
import lethalhabit.util.Direction
import lethalhabit.settings.GameRules.DASH_COOLDOWN
import lethalhabit.settings.GameRules.DASH_STRENGTH
import lethalhabit.settings.GameRules.JUMP_STRENGTH
import lethalhabit.settings.GameRules.WALK_SPEED
import lethalhabit.util.Point
import java.awt.Image
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class CharacterController(private val character: Character) : KeyListener, MouseListener {
    
    private val activeKeys = hashSetOf<Int>()
    private val activeMouseKeys = hashSetOf<Int>()
    
    private val isOnGround
        get() = character.position.y >= GameMap.getHeightAtX(character.position.x)
    
    private var ability1Cooldown = 0
    private var ability2Cooldown = 0
    private var ability3Cooldown = 0
    private var ability4Cooldown = 0
    
    private var isJumping = false
    private var jumpVelocity = 0
    
    private var isDashing = false
    private var dashVelocity = 0
    private var dashCooldown = 0
    
    private var movement = Direction.RIGHT
    
    fun tick() {
        fun handleKeyInputs() {
            activeKeys.forEach {
                when (it) {
                    moveLeft -> {
                        movement = Direction.LEFT
                        character.moveBy(-WALK_SPEED, 0)
                    }
                    moveRight -> {
                        movement = Direction.RIGHT
                        character.moveBy(WALK_SPEED, 0)
                    }
                    jump -> {
                        if (isOnGround && !isJumping) {
                            isJumping = true
                            jumpVelocity = JUMP_STRENGTH
                        }
                    }
                    dash -> {
                        if (isOnGround && !isDashing && dashCooldown <= 0) {
                            isDashing = true
                            dashVelocity = DASH_STRENGTH
                            dashCooldown = DASH_COOLDOWN
                        }
                    }
                    ability1 -> {
                        character.ability1?.let { ability ->
                            if (ability1Cooldown <= 0 && character.slam >= ability.slam) {
                                ability1Cooldown = ability.cooldown
                                character.useSlam(ability.slam)
                                ability.action()
                            }
                        }
                    }
                    ability2 -> {
                        character.ability2?.let { ability ->
                            if (ability2Cooldown <= 0 && character.slam >= ability.slam) {
                                ability2Cooldown = ability.cooldown
                                character.useSlam(ability.slam)
                                ability.action()
                            }
                        }
                    }
                    ability3 -> {
                        character.ability3?.let { ability ->
                            if (ability3Cooldown <= 0 && character.slam >= ability.slam) {
                                ability3Cooldown = ability.cooldown
                                character.useSlam(ability.slam)
                                ability.action()
                            }
                        }
                    }
                    ability4 -> {
                        character.ability4?.let { ability ->
                            if (ability4Cooldown <= 0 && character.slam >= ability.slam) {
                                ability4Cooldown = ability.cooldown
                                character.useSlam(ability.slam)
                                ability.action()
                            }
                        }
                    }
                }
            }
        }
        fun jumping() {
            character.moveBy(0, -jumpVelocity)
            if (isOnGround) {
                character.position = Point(character.position.x, GameMap.getHeightAtX(character.position.x))
                isJumping = false
                jumpVelocity = 0
            } else {
                jumpVelocity -= 1
            }
        }
        fun dashing() {
            if (dashVelocity > 0) {
                character.moveBy(when (movement) {
                    Direction.RIGHT -> -dashVelocity
                    Direction.LEFT -> dashVelocity
                },0)
                dashVelocity -= 2
            } else {
                isDashing = false
            }
            dashCooldown -= 1
        }
        fun abilities() {
            ability1Cooldown--
            ability2Cooldown--
            ability3Cooldown--
            ability4Cooldown--
        }
        handleKeyInputs()
        jumping()
        dashing()
        abilities()
    }
    
    val texture: Image
        get() {
            // TODO: animations, i.e. different textures when jumping, dashing etc.
            return character.texture
        }
    
    val position: Point
        get() = character.position
    
    val hitbox: Hitbox get() = character.hitbox
    
    val hp: Int get() = character.hp
    val slam get() = character.slam
    
    override fun keyTyped(e: KeyEvent) { }
    
    override fun keyPressed(e: KeyEvent) {
        activeKeys.add(e.keyCode)
    }
    
    override fun keyReleased(e: KeyEvent) {
        activeKeys.remove(e.keyCode)
    }
    
    override fun mouseClicked(e: MouseEvent) { }
    
    override fun mousePressed(e: MouseEvent) {
        activeMouseKeys.add(e.button)
    }
    
    override fun mouseReleased(e: MouseEvent) {
        activeMouseKeys.remove(e.button)
    }
    
    override fun mouseEntered(e: MouseEvent) { }
    
    override fun mouseExited(e: MouseEvent) { }
    
}
