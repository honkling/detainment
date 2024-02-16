@file:Listener

package me.honkling.detainment.event

import com.jme3.bullet.objects.PhysicsRigidBody
import me.honkling.detainment.instance
import me.honkling.detainment.lib.getRadius
import me.honkling.detainment.manager.annotations.Listener
import me.honkling.detainment.physics.*
import me.honkling.detainment.registry.ITEMS
import me.honkling.detainment.scheduler
import me.honkling.detainment.space
import me.honkling.detainment.world
import net.kyori.adventure.sound.Sound
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.block.data.BlockData
import org.bukkit.entity.EntityType
import org.bukkit.entity.ItemDisplay
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

private const val RADIUS = 2
private val dynamite = ITEMS.get(NamespacedKey(instance, "dynamite"))!!.getItemStack()

fun onUseDynamite(event: PlayerInteractEvent) {
    val player = event.player
    val clickedBlock = event.clickedBlock
    val inventory = player.inventory

    if (event.action != Action.RIGHT_CLICK_BLOCK || inventory.itemInMainHand != dynamite || clickedBlock!!.type != Material.STONE_BRICKS)
        return

    event.isCancelled = true
    player.inventory.remove(dynamite)

    val affectedBlocks = mutableListOf<Triple<Location, Material, BlockData>>()
    val objects = mutableListOf<PhysicsObject>()

    val location = clickedBlock.location.toCenterLocation()
    for (block in getRadius(location, RADIUS)) {
        if (block.type !in listOf(
            Material.STONE_BRICKS,
            Material.CHISELED_STONE_BRICKS
        ))
            continue

        val location = block.location.toCenterLocation()
        val display = world.spawnEntity(location, EntityType.ITEM_DISPLAY) as ItemDisplay
        display.itemStack = ItemStack(block.type)

        val collision = block.toBulletCollision()
        val body = PhysicsRigidBody(collision, 0.03f)
        val obj = PhysicsObject(display, body)
        spawnObject(obj)

        body.applyCentralForce(player.location.direction.clone()
            .multiply(3f)
            .multiply(Vector(1 + Math.random(), 1 + Math.random(), 1 + Math.random()))
            .toBulletVector())

        affectedBlocks += Triple(location, block.type, block.blockData)
        block.setType(Material.AIR, false)
        objects += obj
    }

    world.spawnParticle(Particle.EXPLOSION_LARGE, clickedBlock.location.toCenterLocation(), 3)

    scheduler.runTaskLater(instance, Runnable {
        for ((location, material, data) in affectedBlocks) {
            location.block.type = material
            location.block.blockData = data
        }

        objects.forEach(::despawnObject)
    }, 5 * 20)
}