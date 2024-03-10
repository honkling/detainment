@file:Listener

package me.honkling.detainment.event

import me.honkling.detainment.instance
import me.honkling.detainment.lib.sendInfo
import me.honkling.detainment.manager.annotations.Listener
import me.honkling.detainment.scheduler
import me.honkling.detainment.world
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.event.player.PlayerJoinEvent

fun onJoinSafeMode(event: PlayerJoinEvent) {
    val player = event.player

    if (player.hasPermission("detainment.admin"))
        return

    player.gameMode = GameMode.SPECTATOR
    player.teleport(Location(world, 0.0, 0.0, -100.0))

    scheduler.runTaskLater(instance, Runnable {
        player.sendInfo("Welcome to <white>Detainment</white>! We are an in-development prison server. You can spectate development here.")
        player.sendInfo("Run <white>/discord</white> to view changelogs and receive progress updates.")
    }, 1L)
}