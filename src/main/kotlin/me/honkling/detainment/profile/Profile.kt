package me.honkling.detainment.profile

import me.honkling.detainment.profile.keys.*
import org.bukkit.entity.Player

private val profiles = mutableMapOf<Player, Profile>()

data class Profile(
    val player: Player
) {
    var escapes = ESCAPES.get(player, 0)
        set(value) = ESCAPES.set(player, value)

    var role = ROLE.get(player, Role.PRISONER)
        set(value) {
            ROLE.set(player, value)
            // todo: give equipment & teleport
        }
}

fun Player.getProfile(): Profile {
    val profile = profiles[this] ?: Profile(this)
    profiles.putIfAbsent(this, profile)
    return profile
}

fun Player.cleanProfile() {
    profiles.remove(this)
}