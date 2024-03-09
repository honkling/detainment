@file:Command("discord")

package me.honkling.detainment.command

import me.honkling.detainment.general
import me.honkling.detainment.lib.sendInfo
import me.honkling.detainment.manager.annotations.Command
import org.bukkit.command.CommandSender

fun discord(executor: CommandSender) {
    executor.sendInfo("<click:open_url:https://discord.gg/${general.discordInvite}>Click to join the Discord server!")
}