package me.honkling.detainment.item

import me.honkling.detainment.instance
import me.honkling.detainment.lib.displayName
import me.honkling.detainment.lib.lore
import org.bukkit.Keyed
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

open class Item(
    id: String,
    val material: Material,
    val displayName: String,
    val isContraband: Boolean,
    vararg val lore: String
) : Keyed {
    private val key = NamespacedKey(instance, id)

    open val enchantments = mutableListOf<Pair<Enchantment, Int>>()

    open fun getItemStack(): ItemStack {
        val item = ItemStack(material)
            .displayName("<i:false><white>$displayName")

        if (isContraband)
            item.lore("<i><red>This item is dangerous.. Don't show it to the guards!")

        for ((enchantment, level) in enchantments) {
            item.addUnsafeEnchantment(enchantment, level)
            item.lore(enchantment.displayName(level))
        }

        item.itemFlags += ItemFlag.HIDE_ENCHANTS
        item.lore(*lore.map { "<i:false><gray>$it" }.toTypedArray())

        return item
    }

    override fun getKey(): NamespacedKey {
        return key
    }
}