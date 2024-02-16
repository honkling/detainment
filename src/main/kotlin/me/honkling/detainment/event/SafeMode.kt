@file:Listener

package me.honkling.detainment.event

import me.honkling.detainment.manager.annotations.Listener
import me.honkling.detainment.world
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.event.player.PlayerJoinEvent

fun onJoin(event: PlayerJoinEvent) {
    val player = event.player

    if (player.hasPermission("detainment.admin"))
        return

    player.gameMode = GameMode.SPECTATOR
    player.teleport(Location(world, 0.0, 0.0, -100.0))
}