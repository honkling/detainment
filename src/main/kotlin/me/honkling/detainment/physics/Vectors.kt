package me.honkling.detainment.physics

import com.jme3.math.Quaternion
import org.bukkit.Location
import org.bukkit.util.Vector
import org.joml.Quaternionf
import org.joml.Vector3f
import com.jme3.math.Vector3f as BulletVector3f

fun Location.toBulletVector(): BulletVector3f {
    return BulletVector3f(x.toFloat(), y.toFloat(), z.toFloat())
}

fun Vector3f.toBulletVector(): BulletVector3f {
    return BulletVector3f(x, y, z)
}

fun Vector.toBulletVector(): BulletVector3f {
    return toVector3f().toBulletVector()
}

fun BulletVector3f.toJOMLVector(): Vector3f {
    return Vector3f(x, y, z)
}

fun Vector3f.toBukkitVector(): Vector {
    return Vector(x, y, z)
}

fun Quaternion.toBukkitQuaternion(): Quaternionf {
    return Quaternionf(x, y, z, w)
}