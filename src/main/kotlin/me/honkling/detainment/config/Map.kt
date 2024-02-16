package me.honkling.detainment.config

import cc.ekblad.toml.decode
import cc.ekblad.toml.tomlMapper
import me.honkling.detainment.instance

private val FILE = instance.dataFolder.resolve("map.toml")

data class Map(
    val punchingBags: List<Location>
)

fun reloadMap(): Map {
    val mapper = tomlMapper {
        mapping<Map>("punching-bags" to "punchingBags")
    }

    if (!FILE.exists()) {
        FILE.parentFile.mkdir()
        instance.saveResource("map.toml", false)
    }

    return mapper.decode(FILE.toPath())
}
