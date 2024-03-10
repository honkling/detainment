// We need to use Java's Integer to avoid some Kotlin primitive goodness
@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package me.honkling.detainment.profile.keys

import me.honkling.detainment.profile.Role
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

val ROLE_TYPE = object : PersistentDataType<Integer, Role> {
    override fun getPrimitiveType(): Class<Integer> {
        return Integer::class.java
    }

    override fun getComplexType(): Class<Role> {
        return Role::class.java
    }

    override fun toPrimitive(complex: Role, context: PersistentDataAdapterContext): Integer {
        return complex.ordinal as Integer
    }

    override fun fromPrimitive(primitive: Integer, context: PersistentDataAdapterContext): Role {
        return Role.entries[primitive.toInt()]
    }
}