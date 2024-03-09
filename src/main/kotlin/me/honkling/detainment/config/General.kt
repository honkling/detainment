package me.honkling.detainment.config

import cc.ekblad.toml.decode
import cc.ekblad.toml.tomlMapper
import me.honkling.detainment.instance

private val FILE = instance.dataFolder.resolve("config.toml")

data class General(
    val discordInvite: String
)

fun reloadGeneral(): General {
    val mapper = tomlMapper {
        mapping<General>("discord-invite" to "discordInvite")
    }

    if (!FILE.exists()) {
        FILE.parentFile.mkdir()
        instance.saveResource("config.toml", false)
    }

    return mapper.decode(FILE.toPath())
}
