package me.honkling.detainment.manager.types

import org.bukkit.command.CommandSender

object BooleanType : Type<Boolean> {
    override fun validate(sender: CommandSender, input: String): Boolean {
        val regex = Regex("^(true|false)(?!\\S)", RegexOption.IGNORE_CASE)
        return regex.containsMatchIn(input)
    }

    override fun parse(sender: CommandSender, input: String): Pair<Boolean, Int> {
        return input.split(" ")[0].toBoolean() to 1
    }

    override fun complete(sender: CommandSender, input: String): List<String> {
        return listOf("true", "false").filter { it.contains(input.lowercase().split(" ")[0]) }
    }
}