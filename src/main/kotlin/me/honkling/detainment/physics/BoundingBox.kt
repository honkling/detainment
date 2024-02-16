package me.honkling.detainment.physics

import com.jme3.bullet.collision.shapes.BoxCollisionShape
import com.jme3.bullet.collision.shapes.CompoundCollisionShape
import com.jme3.bullet.objects.PhysicsRigidBody
import com.jme3.math.Vector3f
import me.honkling.detainment.space
import org.bukkit.block.Block

fun Block.toBulletCollision(): CompoundCollisionShape {
    val shape = CompoundCollisionShape()

    for (aabb in collisionShape.boundingBoxes) {
        val halfX = (aabb.widthX / 2).toFloat()
        val halfY = (aabb.height / 2).toFloat()
        val halfZ = (aabb.widthZ / 2).toFloat()

        val box = BoxCollisionShape(Vector3f(halfX, halfY, halfZ))
        shape.addChildShape(box)
    }

    return shape
}