package me.honkling.detainment.profile

import me.honkling.detainment.lib.propercase
import net.kyori.adventure.text.format.NamedTextColor

enum class Role(val isGuard: Boolean, val prefix: String) {
    PRISONER(false, ""),
    ESCAPEE(false, "");

    override fun toString(): String {
        return name.propercase()
    }
}
