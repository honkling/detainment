package me.honkling.detainment.profile.keys

import me.honkling.detainment.profile.Profile
import kotlin.reflect.KProperty

open class KeyField<T : Any>(
    private val key: Key<*, T>
) {
    open operator fun getValue(thisRef: Profile, property: KProperty<*>): T? {
        return key.get(thisRef.player)
    }

    open operator fun setValue(thisRef: Profile, property: KProperty<*>, value: T?) {
        if (value == null)
            return key.remove(thisRef.player)

        key.set(thisRef.player, value)
    }
}

class RequiredKeyField<T : Any>(key: Key<*, T>, private val defaultValue: T) : KeyField<T>(key) {
    override operator fun getValue(thisRef: Profile, property: KProperty<*>): T {
        return super.getValue(thisRef, property) ?: defaultValue
    }
}