package me.honkling.detainment.manager.types

import org.bukkit.command.CommandSender

object StringType : Type<String> {
    private val regex = Regex("^(\"([^\"]|\\\\\")*\"|\\S+)")

    override fun validate(sender: CommandSender, input: String): Boolean {
        return regex.containsMatchIn(input)
    }

    override fun parse(sender: CommandSender, input: String): Pair<String, Int> {
        val match = regex.find(input)!!.value
        val size = match.split(" ").size

        if (match.startsWith("\"") && match.endsWith("\""))
            return match
                    .substring(1, match.length - 1)
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\") to size

        return match to size
    }

    override fun complete(sender: CommandSender, input: String): List<String> {
        return emptyList()
    }
}