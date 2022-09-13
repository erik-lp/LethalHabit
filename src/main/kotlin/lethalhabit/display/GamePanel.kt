package lethalhabit.display

import lethalhabit.game.Character
import lethalhabit.hitbox.Hitbox
import lethalhabit.settings.*
import lethalhabit.util.Direction
import lethalhabit.util.Point
import java.awt.Graphics
import java.awt.Image
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JPanel

class GamePanel : KeyListener, JPanel() {
    
    private val character = Character(
        "Test",
        Hitbox(listOf(Point(0, 0), Point(0, 100), Point(170, 100), Point(170, 0))),
        "C:\\Users\\erik-\\Pictures\\electric-guitar-hand-drawing-type-sg-not-real-type-35496978.jpg"
    ).also {
        it.position = Point(0, 200)
    }
    
    private val activeKeys = hashSetOf<Int>()
    
    private var floorHeight = 500
    
    private val isOnGround get() = character.position.y >= floorHeight
    
    private var walkSpeed = 5
    
    private var isJumping = false
    private var jumpStrength = 0
    
    private var isDashing = false
    private var dashStrength = 0
    private var dashCooldown = 0
    
    private var movement = Direction.RIGHT
    
    override fun paint(g: Graphics) {
        super.paint(g)
        g.drawImage(character.texture, character.position.x, character.position.y, ::imageUpdate)
    }
    
    override fun repaint() {
        @Suppress("SENSELESS_COMPARISON")
        if (activeKeys == null || character == null) {
            return
        }
        activeKeys.forEach {
            when (it) {
                moveLeft -> {
                    movement = Direction.LEFT
                    character.moveBy(-walkSpeed, 0)
                }
                moveRight -> {
                    movement = Direction.RIGHT
                    character.moveBy(walkSpeed, 0)
                }
                jump -> {
                    if (isOnGround && !isJumping) {
                        isJumping = true
                        jumpStrength = 15
                    }
                }
                dash -> {
                    if (isOnGround && !isDashing && dashCooldown <= 0) {
                        isDashing = true
                        dashStrength = 15
                        dashCooldown = 30
                    }
                }
                ability1 -> println("Ability 1 used")
                ability2 -> println("Ability 2 used")
                ability3 -> println("Ability 3 used")
                ability4 -> println("Ability 4 used")
            }
        }
        jumpingStuff()
        dashingStuff()
        super.repaint()
    }
    
    private fun dashingStuff() {
        if (dashStrength > 0) {
            character.moveBy(when (movement) {
                Direction.RIGHT -> -dashStrength
                Direction.LEFT -> dashStrength
            },0)
            dashStrength -= 2
        } else {
            isDashing = false
        }
        dashCooldown -= 1
    }
    
    private fun jumpingStuff() {
        character.moveBy(0, -jumpStrength)
        jumpStrength -= 1
        if (isOnGround) {
            character.position = Point(character.position.x, floorHeight)
            isJumping = false
        }
    }
    
    override fun keyTyped(e: KeyEvent) { }
    
    override fun keyPressed(e: KeyEvent) {
        activeKeys.add(e.keyCode)
    }
    
    override fun keyReleased(e: KeyEvent) {
        activeKeys.remove(e.keyCode)
    }
    
}
