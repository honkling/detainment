package me.honkling.detainment.lib

import me.honkling.detainment.instance

fun <T> scanForRegistrable(pkg: String, registrable: Class<out T>): List<T> {
    val clazz = instance::class.java
    val classes = getClassesInPackage(clazz, "me.honkling.detainment.$pkg") { clazz ->
        val constructor = clazz.constructors.find { it.parameterCount == 0 && it.canAccess(null) }
        val instance = clazz.fields.find { it.name == "INSTANCE" }
        registrable.isAssignableFrom(clazz) && clazz != registrable && (constructor != null || instance != null)
    } as List<Class<out T>>

    return classes.map { clazz ->
        val instanceField = clazz.fields.find { it.name == "INSTANCE" }

        if (instanceField != null) instanceField[null] as T
        else clazz.getConstructor().newInstance() as T
    }
}