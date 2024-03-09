@file:Listener

package me.honkling.detainment.event

import me.honkling.detainment.manager.annotations.Listener
import me.honkling.detainment.task.Watch.bossbar
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

fun onLifecycleStart(event: PlayerJoinEvent) {
    val player = event.player

    if (!player.hasPlayedBefore())
        return

    player.showBossBar(bossbar)
}

fun onLifecycleStop(event: PlayerQuitEvent) {
    val player = event.player
    player.hideBossBar(bossbar)
}