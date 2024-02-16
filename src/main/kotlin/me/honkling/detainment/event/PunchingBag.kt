@file:Listener

package me.honkling.detainment.event

import com.jme3.bullet.objects.PhysicsRigidBody
import me.honkling.detainment.lib.*
import me.honkling.detainment.manager.annotations.Listener
import me.honkling.detainment.physics.getObjectByDisplay
import me.honkling.detainment.physics.toBulletVector
import org.bukkit.entity.Interaction
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.joml.Quaternionf
import org.joml.Vector2d
import org.joml.Vector3f
import kotlin.math.atan2

fun onPunch(event: EntityDamageByEntityEvent) {
    val player = event.damager
    val interaction = event.entity

    if (player !is Player || interaction !is Interaction)
        return

    val display = interaction.passengers.find { it is ItemDisplay } as ItemDisplay? ?: return
    val obj = getObjectByDisplay(display) ?: return
    val body = obj.body as PhysicsRigidBody

    body.applyCentralForce(player.location.direction.multiply(40).toBulletVector())
}