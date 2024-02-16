@file:Command("simulation", permission = "detainment.admin")

package me.honkling.detainment.command

import com.jme3.bullet.collision.shapes.BoxCollisionShape
import com.jme3.bullet.objects.PhysicsRigidBody
import com.jme3.math.Vector3f
import me.honkling.detainment.lib.sendNegative
import me.honkling.detainment.lib.sendSuccess
import me.honkling.detainment.manager.annotations.Command
import me.honkling.detainment.physics.*
import me.honkling.detainment.space
import me.honkling.detainment.world
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun start(player: Player) {
    if (!resumeSimulation()) {
        player.sendNegative("The simulation is already running.")
        return
    }

    player.sendSuccess("Started the simulation.")
}

fun stop(player: Player) {
    if (!pauseSimulation()) {
        player.sendNegative("The simulation isn't running.")
        return
    }

    player.sendSuccess("Stopped the simulation.")
}

fun speed(player: Player, ticks: Int) {
    simulationSpeed = ticks
    player.sendSuccess("Set the simulation speed to <white>${21 - simulationSpeed} ticks per second</white>.")
}

fun cube(player: Player) {
    val location = player.location.clone()
    val display = world.spawnEntity(location, EntityType.ITEM_DISPLAY) as ItemDisplay
    display.itemStack = ItemStack(Material.STONE)

    val cube = BoxCollisionShape(0.5f)
    val body = PhysicsRigidBody(cube, 1f)

    spawnObject(PhysicsObject(display, body))
}