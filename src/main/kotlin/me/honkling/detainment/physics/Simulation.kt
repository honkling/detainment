package me.honkling.detainment.physics

import com.jme3.bullet.objects.PhysicsRigidBody
import com.jme3.math.Quaternion
import com.jme3.math.Vector3f
import me.honkling.detainment.instance
import me.honkling.detainment.lib.getRadius
import me.honkling.detainment.lib.setLeftRotation
import me.honkling.detainment.lib.setTranslation
import me.honkling.detainment.mapEntities
import me.honkling.detainment.scheduler
import me.honkling.detainment.space
import org.bukkit.Location
import org.bukkit.entity.Display

var simulationSpeed = 20
var objects = mutableMapOf<Display, PhysicsObject>()

private var blocks = mutableMapOf<Location, PhysicsRigidBody>()
private var taskId: Int? = null

/**
 * @returns true if the simulation was started, false if it wasn't ever stopped
 */
fun resumeSimulation(): Boolean {
    if (taskId != null)
        return false

    taskId = scheduler.scheduleSyncRepeatingTask(instance, ::stepSimulation, 0L, 1L)
    return true
}

/**
 * @returns true if the simulation was stopped, false if it wasn't ever started
 */
fun pauseSimulation(): Boolean {
    if (taskId == null)
        return false

    scheduler.cancelTask(taskId!!)
    taskId = null
    return true
}

fun getObjectByDisplay(display: Display): PhysicsObject? {
    return objects[display]
}

/**
 * Spawns an object in the simulation and updates the display entity's position & rotation automatically.
 */
fun spawnObject(obj: PhysicsObject) {
    mapEntities += obj.associates
    mapEntities += obj.display
    space.addCollisionObject(obj.body)
    obj.body.setPhysicsLocation(obj.origin.toBulletVector())
    objects[obj.display] = obj
}

/**
 * Despawns an object and its display.
 */
fun despawnObject(obj: PhysicsObject) {
    space.remove(obj.body)
    obj.display.remove()
    objects.remove(obj.display)
    mapEntities -= obj.display
    mapEntities -= obj.associates.toSet()
}

/**
 * Steps the simulation one tick.
 */
fun stepSimulation() {
    var removed = false
    space.update(1f / simulationSpeed, 5)

    for ((display, obj) in objects) {
        val (_, body, origin, onFinishTick) = obj
        val location = body.getPhysicsLocation(Vector3f(0f, 0f, 0f))
        val rotation = body.getPhysicsRotation(Quaternion(0f, 0f, 0f, 0f))
        val translation = location.toJOMLVector().sub(origin)

        display.interpolationDelay = 0
        display.interpolationDuration = 1
        display.transformation = display.transformation
            .setTranslation(translation)
            .setLeftRotation(rotation.toBukkitQuaternion())

        val nearbyBlocks = getRadius(display.location.clone().add(translation.toBukkitVector()), 5)
        for (block in nearbyBlocks) {
            val blockLocation = block.location
            val type = block.type

            if (type.isAir) {
                if (blockLocation in blocks) {
                    space.remove(blocks.remove(blockLocation)!!)
                    removed = true
                }

                continue
            }

            if (blockLocation in blocks || !type.isCollidable)
                continue

            val collision = block.toBulletCollision()
            val rigidBody = PhysicsRigidBody(collision, 0f)
            space.add(rigidBody)
            rigidBody.setPhysicsLocation(blockLocation.toCenterLocation().toBulletVector())
            rigidBody.setGravity(Vector3f.ZERO)

            blocks[blockLocation] = rigidBody
        }

        onFinishTick.invoke(display, body)
    }

    if (removed)
        space.activateAll(true)
}