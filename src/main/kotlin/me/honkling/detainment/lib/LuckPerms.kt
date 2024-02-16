package me.honkling.detainment.lib

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.user.User
import org.bukkit.entity.Player

val luckPerms = LuckPermsProvider.get()

fun Player.getUser(): User {
    return luckPerms.getPlayerAdapter(Player::class.java).getUser(this)
}

fun Player.getPrefix(): Component {
    return getUser().cachedData.metaData.prefix?.mm()
        ?: Component.empty()
}

fun Player.getColor(): NamedTextColor {
    val group = getUser().primaryGroup

    return if (group == "default") NamedTextColor.GRAY
           else NamedTextColor.WHITE
}