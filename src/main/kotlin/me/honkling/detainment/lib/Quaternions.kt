package me.honkling.detainment.lib

import org.joml.Quaternionf
import org.joml.Vector3i
import kotlin.math.cos
import kotlin.math.sin

fun fromAxisAngle(axis: Vector3i, angle: Double): Quaternionf {
    val sinAngle = sin(angle / 2)

    val qx = axis.x * sinAngle
    val qy = axis.y * sinAngle
    val qz = axis.z * sinAngle
    val qw = cos(angle / 2)

    return Quaternionf(qx, qy, qz, qw)
}