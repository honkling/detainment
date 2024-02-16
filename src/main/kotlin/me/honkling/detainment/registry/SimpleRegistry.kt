package me.honkling.detainment.registry

import me.honkling.detainment.lib.scanForRegistrable
import org.bukkit.Keyed
import org.bukkit.NamespacedKey
import org.bukkit.Registry
import java.util.stream.Stream

val ITEMS = ItemRegistry()

open class SimpleRegistry<T : Keyed>(val pkg: String, val clazz: Class<T>) : Registry<T> {
    protected val entries = mutableMapOf<NamespacedKey, T>()

    fun register() {
        val registrables = scanForRegistrable(pkg, clazz)

        for (registrable in registrables)
            entries[registrable.key] = registrable
    }

    override fun iterator(): MutableIterator<T> {
        return entries.values.iterator()
    }

    override fun get(key: NamespacedKey): T? {
        return entries[key]
    }

    fun stream(): Stream<T> {
        return entries.values.stream()
    }
}