package me.honkling.detainment.config

import me.honkling.detainment.world
import org.bukkit.Location

data class Location(
    val x: Double,
    val y: Double,
    val z: Double
) {
    val bukkit = Location(world, x, y, z)
}
