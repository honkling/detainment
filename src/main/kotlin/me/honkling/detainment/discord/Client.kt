package me.honkling.detainment.discord

import me.honkling.detainment.general
import net.dv8tion.jda.api.JDABuilder

val jda = JDABuilder.createDefault(general.discord.token)
    .build()