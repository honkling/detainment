@file:Listener

package me.honkling.detainment.event

import me.honkling.detainment.manager.annotations.Listener
import net.kyori.adventure.sound.Sound
import org.bukkit.NamespacedKey
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

fun onFirstJoin(event: PlayerJoinEvent) {
    val player = event.player

    if (player.hasPlayedBefore())
        return

    player.addPotionEffect(PotionEffect(
        PotionEffectType.BLINDNESS,
        5,
        1,
        false,
        false,
        false
    ))

    player.playSound(Sound.sound {
        it.type(NamespacedKey.minecraft("block.conduit.ambient"))
    })
}