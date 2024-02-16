package me.honkling.detainment.manager.types

import org.bukkit.command.CommandSender

object IntegerType : Type<Int> {
    override fun validate(sender: CommandSender, input: String): Boolean {
        val regex = Regex("^\\d+(?!\\S)")
        return regex.containsMatchIn(input)
    }

    override fun parse(sender: CommandSender, input: String): Pair<Int, Int> {
        return input.split(" ")[0].toInt() to 1
    }

    override fun complete(sender: CommandSender, input: String): List<String> {
        return emptyList()
    }
}