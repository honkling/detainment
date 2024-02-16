package me.honkling.detainment.manager.types

import org.bukkit.command.CommandSender

object DoubleType : Type<Double> {
    override fun validate(sender: CommandSender, input: String): Boolean {
        val regex = Regex("^\\d+(\\.\\d+(?!\\S))?")
        return regex.containsMatchIn(input)
    }

    override fun parse(sender: CommandSender, input: String): Pair<Double, Int> {
        return input.split(" ")[0].toDouble() to 1
    }

    override fun complete(sender: CommandSender, input: String): List<String> {
        return emptyList()
    }
}