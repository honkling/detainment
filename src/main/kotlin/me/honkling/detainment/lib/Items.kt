package me.honkling.detainment.lib

import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

fun ItemStack.displayName(displayName: String): ItemStack {
    editMeta {
        it.displayName(displayName.mm())
    }

    return this
}

fun ItemStack.lore(vararg lore: Component): ItemStack {
    val existingLore = lore() ?: mutableListOf()
    existingLore += lore
    lore(existingLore)

    return this
}

fun ItemStack.lore(vararg lore: String): ItemStack {
    return lore(*lore.map(String::mm).toTypedArray())
}