package me.honkling.detainment.manager.tree

import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.java.JavaPlugin

class CommandNode(
        parent: Node?,
        name: String,
        val aliases: List<String>,
        val description: String,
        val usage: String,
        val permission: String,
        val permissionMessage: String
) : Node(parent, name) {
    fun createPluginCommand(instance: JavaPlugin): PluginCommand {
        val mm = MiniMessage.miniMessage()
        val constructor = PluginCommand::class.java.declaredConstructors[0]
        constructor.isAccessible = true
        val command = constructor.newInstance(name, instance) as PluginCommand

        command.description = description
        command.usage = LegacyComponentSerializer.legacySection().serialize(mm.deserialize(usage.replace("{0}", name)))
        command.aliases = aliases
        command.permission = permission.replace("{0}", name)
        command.permissionMessage(mm.deserialize(permissionMessage))

        return command
    }

    override fun toString(): String {
        return "CommandNode(name=$name)"
    }
}