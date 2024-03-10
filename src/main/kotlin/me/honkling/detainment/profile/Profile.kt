package me.honkling.detainment.profile

import me.honkling.detainment.profile.keys.*
import org.bukkit.entity.Player

private val profiles = mutableMapOf<Player, Profile>()

data class Profile(
    val player: Player
) {
    var escapes by RequiredKeyField(ESCAPES, 0)
    var role by RequiredKeyField(ROLE, Role.PRISONER)
}

fun Player.getProfile(): Profile {
    val profile = profiles[this] ?: Profile(this)
    profiles.putIfAbsent(this, profile)
    return profile
}

fun Player.cleanProfile() {
    profiles.remove(this)
}