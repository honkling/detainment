package me.honkling.detainment.manager.types

import me.honkling.detainment.profile.Role
import org.bukkit.command.CommandSender

object RoleType : Type<Role> {
    override fun validate(sender: CommandSender, input: String): Boolean {
        return input.split(" ")[0].uppercase() in Role.entries.map(Role::name)
    }

    override fun parse(sender: CommandSender, input: String): Pair<Role, Int> {
        return Role.valueOf(input.split(" ")[0]) to 1
    }

    override fun complete(sender: CommandSender, input: String): List<String> {
        return Role.entries
            .filter { input.split(" ")[0] in it.name }
            .map { it.name }
    }
}