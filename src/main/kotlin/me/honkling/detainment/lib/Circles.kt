package me.honkling.detainment.lib

import org.bukkit.Location
import org.joml.Vector2d
import kotlin.math.cos
import kotlin.math.sin

//fun Location.getPointOnSphere(radius: Double, x: Float, z: Float): Location {
//
//}

fun Float.toDegrees(): Float {
    return this * (180f / Math.PI.toFloat())
}

fun Float.toRadian(): Float {
    return this / (180f / Math.PI.toFloat())
}