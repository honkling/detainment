@file:Listener

package me.honkling.detainment.event

import io.papermc.paper.chat.ChatRenderer
import io.papermc.paper.event.player.AsyncChatEvent
import me.honkling.detainment.lib.getPrefix
import me.honkling.detainment.lib.mm
import me.honkling.detainment.manager.annotations.Listener
import me.honkling.detainment.profile.getProfile
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

object Renderer : ChatRenderer {
    override fun render(source: Player, sourceDisplayName: Component, message: Component, viewer: Audience): Component {
        val profile = source.getProfile()
        val prefix = source.getPrefix(" ")
        val role = profile.role.getPrefix()

        return Component.empty()
            .color(NamedTextColor.GRAY)
            .append(prefix)
            .append(role
                .append(source.name()))
            .append(Component.text(":")
                .append(Component.space())
                .append(message))

    }
}

fun onChat(event: AsyncChatEvent) {
    event.renderer(Renderer)
}