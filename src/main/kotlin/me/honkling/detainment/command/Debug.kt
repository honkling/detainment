@file:Command("debug", permission = "detainment.admin")

package me.honkling.detainment.command

import me.honkling.detainment.item.Item
import me.honkling.detainment.lib.sendNegative
import me.honkling.detainment.lib.sendSuccess
import me.honkling.detainment.manager.annotations.Command
import me.honkling.detainment.manager.annotations.Optional
import me.honkling.detainment.physics.objects.spawnPunchingBag
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun give(sender: CommandSender, target: Player, item: Item, @Optional amount: Int?) {
    val newAmount = amount ?: 1

    if (newAmount < 0) {
        sender.sendNegative("Amount must be greater than or equal to 0")
        return
    }

    val itemStack = item.getItemStack().asQuantity(newAmount)
    target.inventory.addItem(itemStack)
    sender.sendSuccess("Gave <white>${newAmount}x ${item.displayName}</white> to <white>${target.name}</white>")
}

fun strength(player: Player) {
    spawnPunchingBag(player.location)
}