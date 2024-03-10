package me.honkling.detainment.manager

import me.honkling.detainment.item.Item
import me.honkling.detainment.manager.tree.CommandNode
import me.honkling.detainment.manager.tree.SubcommandNode
import me.honkling.detainment.manager.types.*
import me.honkling.detainment.profile.Role
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.Array.*

class CommandManager(val instance: JavaPlugin) {
    val commands = mutableMapOf<String, CommandNode>()
    val types = mutableMapOf<Class<*>, Type<*>>(
        Boolean::class.java to BooleanType,
        Double::class.java to DoubleType,
        Int::class.java to IntegerType,
        java.lang.Boolean::class.java to BooleanType,
        java.lang.Double::class.java to DoubleType,
        java.lang.Integer::class.java to IntegerType,
        OfflinePlayer::class.java to OfflinePlayerType,
        Player::class.java to PlayerType,
        String::class.java to StringType,
        Item::class.java to ItemType,
        Role::class.java to RoleType
    )

    fun registerCommands(vararg packages: String) {
        for (pkg in packages)
            for (command in scanForCommands(this, pkg))
                commands[command.name.lowercase()] = command

        val commandMap = Bukkit.getCommandMap()

        for ((_, node) in commands) {
            val command = node.createPluginCommand(instance)
            command.setExecutor(::onCommand)

            command.setTabCompleter { sender, command, _, args ->
                return@setTabCompleter tabComplete(this, sender, command, args)
            }

            commandMap.register(instance.name, command)
        }
    }

    private fun onCommand(sender: CommandSender, bukkitCommand: Command, label: String, args: Array<String>): Boolean {
        val command = commands[bukkitCommand.name.lowercase()] ?: return false
        val (subcommand, parameters) = getCommand(sender, command, args.toList()) ?: return false

        subcommand.method.invoke(null, sender, *parameters.toTypedArray())

        return true
    }

    private fun getCommand(sender: CommandSender, command: CommandNode, args: List<String>): Pair<SubcommandNode, List<Any>>? {
        val postFirst = args.slice(1 until args.size)

        for (node in command.children) {
            when (node) {
                is CommandNode -> {
                    if (args.isEmpty())
                        continue

                    return getCommand(sender, node, postFirst) ?: continue
                }
                is SubcommandNode -> {
                    if (node.name != command.name && (args.isEmpty() || args.first().lowercase() != node.name))
                        continue

                    val (isValid, parameters) = parseArguments(sender, node, if (node.name == command.name) args else postFirst)

                    if (!isValid)
                        continue

                    return node to parameters
                }
            }
        }

        return null
    }

    private fun parseArguments(sender: CommandSender, command: SubcommandNode, args: List<String>): Pair<Boolean, List<Any>> {
        @Suppress("NAME_SHADOWING") var args = args
        val parameters = mutableListOf<Any>()

        for ((index, parameter) in command.parameters.withIndex()) {
            if (parameter.greedy && index + 1 < command.parameters.size) {
                instance.logger.warning("Vararg parameter '${parameter.name}' must be the last parameter")
                return false to parameters
            }

            if (args.isEmpty())
                return !parameter.required to parameters

            val type = types[parameter.type]

            if (type == null) {
                instance.logger.warning("Failed to find type for class '${parameter.type.name}'. Did you delete the type after registering commands?")
                return false to parameters
            }

            val spread = mutableListOf<Any>()
            var doneOnce = false

            while ((args.isNotEmpty() && parameter.greedy) || !doneOnce) {
                val input = args.joinToString(" ")

                if (!type.validate(sender, input))
                    return false to parameters

                val (parsed, size) = type.parse(sender, input)
                args = args.slice(size until args.size)

                if (parameter.greedy) spread.add(parsed!!)
                else parameters.add(parsed!!)

                doneOnce = true
            }

            if (parameter.greedy) {
                val array = newInstance(parameter.type, spread.size)

                for ((i, parsed) in spread.withIndex())
                    set(array, i, parsed)

                parameters.add(array)
            }
        }

        return true to parameters
    }
}