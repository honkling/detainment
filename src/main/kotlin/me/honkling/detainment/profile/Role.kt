package me.honkling.detainment.profile

import me.honkling.detainment.lib.mm
import me.honkling.detainment.lib.propercase
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

enum class Role(val isGuard: Boolean, val prefix: String) {
    PRISONER(false, ""),
    ESCAPEE(false, "<red><dark_gray>(<red>Escapee</red>)</dark_gray><red>"),
    GUARD(true, "<blue><dark_gray>(<blue>Guard</blue>)</dark_gray><blue>"),
    WARDEN(true, "<red><dark_gray>(<red>Warden</red>)</dark_gray><red>");

    fun getPrefix(): Component {
        return if (prefix == "") Component.empty()
               else prefix.mm().append(Component.space())
    }

    override fun toString(): String {
        return name.propercase()
    }
}
