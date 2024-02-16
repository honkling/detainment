package me.honkling.detainment.profile.keys

import me.honkling.detainment.profile.Role
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

val ROLE_TYPE = object : PersistentDataType<Int, Role> {
    override fun getPrimitiveType(): Class<Int> {
        return Int::class.java
    }

    override fun getComplexType(): Class<Role> {
        return Role::class.java
    }

    override fun toPrimitive(complex: Role, context: PersistentDataAdapterContext): Int {
        return complex.ordinal
    }

    override fun fromPrimitive(primitive: Int, context: PersistentDataAdapterContext): Role {
        return Role.entries[primitive]
    }
}