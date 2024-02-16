package me.honkling.detainment.lib

fun String.propercase(): String {
    return this
        .split("_")
        .joinToString(" ") { it[0] + it.substring(1).lowercase() }
}