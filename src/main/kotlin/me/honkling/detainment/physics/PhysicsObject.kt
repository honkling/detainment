package me.honkling.detainment.physics

import com.jme3.bullet.objects.PhysicsBody
import org.bukkit.entity.Display
import org.bukkit.entity.Entity
import org.joml.Vector3f

data class PhysicsObject(
    val display: Display,
    val body: PhysicsBody,
    val origin: Vector3f = display.location.toVector().toVector3f(),
    val onFinishTick: Display.(PhysicsBody) -> Unit = {},
    val associates: List<Entity> = emptyList()
)
