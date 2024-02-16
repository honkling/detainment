package me.honkling.detainment.lib

import me.honkling.detainment.world
import org.bukkit.Location
import org.bukkit.block.Block
import kotlin.math.pow

fun getRadius(center: Location, radius: Int): List<Block> {
    val blocks = mutableListOf<Block>()

    val minX = center.x - radius
    val minY = center.y - radius
    val minZ = center.z - radius

    var xOffset = 0
    var yOffset = 0
    var zOffset = 0

    for (i in 0..<(radius * 2 + 1).toDouble().pow(3.0).toInt()) {
        val location = Location(world, minX + xOffset, minY + yOffset, minZ + zOffset)

        if (location.toCenterLocation().distance(center) <= radius)
            blocks += location.block

        xOffset++

        if (xOffset > radius * 2) {
            xOffset = 0
            zOffset++
        }

        if (zOffset > radius * 2) {
            xOffset = 0
            zOffset = 0
            yOffset++
        }
    }

    return blocks
}