package me.honkling.detainment.manager.types

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

object PlayerType : Type<OfflinePlayer> {
    override fun validate(sender: CommandSender, input: String): Boolean {
        return Bukkit.getPlayerExact(input.split(" ")[0]) != null
    }

    override fun parse(sender: CommandSender, input: String): Pair<OfflinePlayer, Int> {
        return Bukkit.getPlayerExact(input.split(" ")[0])!! to 1
    }

    override fun complete(sender: CommandSender, input: String): List<String> {
        return Bukkit
                .getOnlinePlayers()
                .map { it.name }
                .filter { input.split(" ")[0].lowercase() in it.lowercase() }
    }
}