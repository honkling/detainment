package me.honkling.detainment.manager.types

import org.bukkit.command.CommandSender

interface Type<T> {
    fun validate(sender: CommandSender, input: String): Boolean
    fun parse(sender: CommandSender, input: String): Pair<T, Int>
    fun complete(sender: CommandSender, input: String): List<String>
}