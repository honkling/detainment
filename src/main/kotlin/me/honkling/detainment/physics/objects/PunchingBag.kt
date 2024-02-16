package me.honkling.detainment.physics.objects

import com.jme3.bullet.RotationOrder
import com.jme3.bullet.collision.shapes.BoxCollisionShape
import com.jme3.bullet.joints.New6Dof
import com.jme3.bullet.objects.PhysicsRigidBody
import com.jme3.math.Matrix3f
import com.jme3.math.Vector3f
import me.honkling.detainment.lib.setScale
import me.honkling.detainment.lib.setTranslation
import me.honkling.detainment.physics.PhysicsObject
import me.honkling.detainment.physics.spawnObject
import me.honkling.detainment.physics.toBulletVector
import me.honkling.detainment.space
import me.honkling.detainment.world
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Display
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Interaction
import org.bukkit.entity.ItemDisplay
import org.bukkit.inventory.ItemStack

fun spawnPunchingBag(origin: Location) {
    val location = origin.toCenterLocation().add(0.0, 2.5, 0.0)

    val (bagDisplay, interaction) = spawnBagDisplay(location)
    val bag = spawnBag(bagDisplay)

    val chainDisplay = spawnChainDisplay(location)
    val chain = spawnChain(chainDisplay)

    // Make a joint connecting the top of the chain to the ceiling
    val ceilingJoint = New6Dof(chain, Vector3f(0f, 0.5f, 0f), chainDisplay.location.toBulletVector().add(0f, 1f, 0f), Matrix3f.IDENTITY, Matrix3f.IDENTITY, RotationOrder.XYZ)
    space.addJoint(ceilingJoint)

    // Make a joint connecting the top of the bag to the bottom of the chain
    val joint = New6Dof(bag, chain, Vector3f(0f, 1.25f, 0f), Vector3f(0f, -0.5f, 0f), Matrix3f.IDENTITY, Matrix3f.IDENTITY, RotationOrder.XYZ)
    space.addJoint(joint)

    return
}

private fun spawnChain(display: Display): PhysicsRigidBody {
    val box = BoxCollisionShape(Vector3f(0.1f, 0.5f, 0.1f))
    val body = PhysicsRigidBody(box, 0.05f)
    body.angularDamping = 0.75f
    body.angularSleepingThreshold = 0.01f

    spawnObject(PhysicsObject(display, body))

    return body
}

private fun spawnBag(display: Display): PhysicsRigidBody {
    val box = BoxCollisionShape(Vector3f(0.4f, 1.25f, 0.4f))
    val body = PhysicsRigidBody(box, 0.4f)
    body.angularDamping = 0.75f
    body.angularSleepingThreshold = 0.01f

    spawnObject(PhysicsObject(display, body))

    return body
}

private fun spawnChainDisplay(location: Location): Display {
    val display = world.spawnEntity(location, EntityType.ITEM_DISPLAY) as ItemDisplay
    display.itemStack = ItemStack(Material.CHAIN)
    return display
}

private fun spawnBagDisplay(location: Location): Pair<Display, Interaction> {
    val display = world.spawnEntity(location, EntityType.ITEM_DISPLAY) as ItemDisplay
    val interaction = world.spawnEntity(location, EntityType.INTERACTION) as Interaction

    interaction.interactionHeight = -2.5f
    interaction.interactionWidth = 0.8f

    display.itemStack = ItemStack(Material.RED_WOOL)
    display.transformation = display.transformation
        .setTranslation(org.joml.Vector3f(0f, -1.25f, 0f))
        .setScale(org.joml.Vector3f(0.8f, 2.5f, 0.8f))

    interaction.addPassenger(display)
    return display to interaction
}