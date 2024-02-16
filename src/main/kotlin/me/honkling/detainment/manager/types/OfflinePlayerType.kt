package me.honkling.detainment.manager.types

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

object OfflinePlayerType : Type<OfflinePlayer> {
    override fun validate(sender: CommandSender, input: String): Boolean {
        return true
    }

    override fun parse(sender: CommandSender, input: String): Pair<OfflinePlayer, Int> {
        return Bukkit.getOfflinePlayer(input.split(" ")[0]) to 1
    }

    override fun complete(sender: CommandSender, input: String): List<String> {
        return Bukkit
                .getOnlinePlayers()
                .map { it.name }
                .filter { it.contains(input.split(" ")[0]) }
    }
}