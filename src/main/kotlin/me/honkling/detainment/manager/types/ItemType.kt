package me.honkling.detainment.manager.types

import me.honkling.detainment.instance
import me.honkling.detainment.item.Item
import me.honkling.detainment.registry.ITEMS
import org.bukkit.NamespacedKey
import org.bukkit.command.CommandSender

object ItemType : Type<Item> {
    override fun validate(sender: CommandSender, input: String): Boolean {
        val first = input.split(" ")[0]

        if (first.isEmpty())
            return false

        val key = NamespacedKey.fromString(first, instance) ?: return false
        return ITEMS.get(key) != null
    }

    override fun parse(sender: CommandSender, input: String): Pair<Item, Int> {
        val key = NamespacedKey.fromString(input.split(" ")[0], instance)!!
        return ITEMS.get(key)!! to 1
    }

    override fun complete(sender: CommandSender, input: String): List<String> {
        val first = input.split(" ")[0]
        return ITEMS.stream()
            .map { it.key.asString() }
            .filter { first in it }
            .toList()
    }
}