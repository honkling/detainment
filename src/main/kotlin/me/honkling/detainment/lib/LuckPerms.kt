package me.honkling.detainment.lib

import me.honkling.detainment.profile.Role
import me.honkling.detainment.profile.getProfile
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.user.User
import org.bukkit.entity.Player

val luckPerms = LuckPermsProvider.get()

fun Player.getUser(): User {
    return luckPerms.getPlayerAdapter(Player::class.java).getUser(this)
}

fun Player.getPrefix(suffix: String = ""): Component {
    return getUser().cachedData.metaData.prefix?.plus(suffix)?.mm()
        ?: Component.empty()
}