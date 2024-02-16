package me.honkling.detainment.lib

import me.honkling.detainment.profile.Role
import me.honkling.detainment.profile.getProfile
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Bukkit

val mm = MiniMessage.miniMessage()

fun String.mm(vararg placeholders: TagResolver): Component {
    return mm.deserialize(this, *placeholders)
}

fun Audience.sendSuccess(message: String) {
    sendMiniMessage("<color:#bfffc6>✔</color> <dark_gray>»</dark_gray> <gray>$message")
}

fun Audience.sendInfo(message: String) {
    sendMiniMessage("<b>!</b> <dark_gray>»</dark_gray> <gray>$message")
}

fun Audience.sendNegative(message: String) {
    sendMiniMessage("<color:#ff6e6e>✖</color> <dark_gray>»</dark_gray> <gray>$message")
}

fun Audience.sendIntercom(message: String) {
    sendMiniMessage("\n<blue><b>Intercom</b> <dark_gray>›</dark_gray> <blue>$message\n")
}

fun broadcastToRole(role: Role, message: String) {
    broadcastToRole(role, message.mm())
}

fun broadcastToRole(role: Role, message: Component) {
    for (player in Bukkit.getOnlinePlayers())
        if (player.getProfile().role == role)
            player.sendMessage(message)
}

private fun Audience.sendMiniMessage(message: String) {
    sendMessage(message.mm())
}