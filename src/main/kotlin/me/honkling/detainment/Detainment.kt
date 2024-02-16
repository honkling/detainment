package me.honkling.detainment

import com.jme3.bullet.PhysicsSpace
import com.jme3.bullet.collision.shapes.BoxCollisionShape
import com.jme3.bullet.collision.shapes.PlaneCollisionShape
import com.jme3.bullet.objects.PhysicsBody
import com.jme3.bullet.objects.PhysicsRigidBody
import com.jme3.math.Plane
import com.jme3.math.Vector3f
import com.jme3.system.NativeLibraryLoader
import me.honkling.detainment.config.Map
import me.honkling.detainment.config.reloadMap
import me.honkling.detainment.manager.CommandManager
import me.honkling.detainment.physics.bulletFile
import me.honkling.detainment.physics.downloadNativeLibrary
import me.honkling.detainment.physics.objects.spawnPunchingBag
import me.honkling.detainment.physics.resumeSimulation
import me.honkling.detainment.registry.ITEMS
import me.honkling.knockffa.manager.ListenerManager
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files

val instance = JavaPlugin.getPlugin(Detainment::class.java)
val logger = instance.logger

lateinit var space: PhysicsSpace
lateinit var world: World
lateinit var map: Map

val pluginManager = Bukkit.getPluginManager()
val scheduler = Bukkit.getScheduler()

private val bulletDirectory = Files.createTempDirectory(instance.dataFolder.toPath(), "bullet").toFile()
private val mapEntities = mutableListOf<Entity>()

class Detainment : JavaPlugin() {
    override fun onEnable() {
        world = Bukkit.getWorld("world")!!
        map = reloadMap()

        scheduler.runTaskAsynchronously(instance, Runnable { downloadNativeLibrary(::setupPhysics) })
        setupRegistries()
        setupCommands()
        setupEvents()
    }

    override fun onDisable() {
        bulletDirectory.deleteRecursively()
        removeMapEntities()
    }

    private fun removeMapEntities(entities: MutableList<Entity> = mapEntities) {
        for (entity in entities) {
            entity.passengers.forEach { removeMapEntities(mutableListOf(it)) }
            entity.remove()
        }

        entities.clear()
    }

    private fun setupPhysics() {
        try {
            bulletFile.copyTo(bulletDirectory.resolve(bulletFile.name))
            bulletFile.deleteOnExit()

            NativeLibraryLoader.loadLibbulletjme(true, bulletDirectory, "Debug", "Sp")
        } catch (ignored: UnsatisfiedLinkError) {} // in case we reload the plugin

        space = PhysicsSpace(PhysicsSpace.BroadphaseType.DBVT)

        resumeSimulation()
        setupMap()
    }

    private fun setupRegistries() {
        ITEMS.register()
    }

    private fun setupCommands() {
        val manager = CommandManager(this)
        manager.registerCommands("me.honkling.detainment.command")
    }

    private fun setupEvents() {
        val manager = ListenerManager(this)
        manager.registerListeners("me.honkling.detainment.event")
    }

    private fun setupMap() {
        for (location in map.punchingBags)
            spawnPunchingBag(location.bukkit)
    }
}