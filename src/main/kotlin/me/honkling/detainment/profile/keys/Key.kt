package me.honkling.detainment.profile.keys

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType

val ROLE = Key("role", ROLE_TYPE)
val ESCAPES = Key("escapes", PersistentDataType.INTEGER)

data class Key<T, Z : Any>(
    private val id: String,
    private val type: PersistentDataType<T, Z>
) {
    private val key = NamespacedKey("prison", id)

    fun get(holder: PersistentDataHolder): Z? {
        val container = holder.persistentDataContainer
        return container.get(key, type)
    }

    fun get(holder: PersistentDataHolder, value: Z): Z {
        return get(holder) ?: value
    }

    fun set(holder: PersistentDataHolder, value: Z) {
        val container = holder.persistentDataContainer
        container.set(key, type, value)
    }

    fun has(holder: PersistentDataHolder): Boolean {
        val container = holder.persistentDataContainer
        return container.has(key)
    }
}
