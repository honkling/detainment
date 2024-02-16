package me.honkling.detainment.physics

import me.honkling.detainment.instance
import me.honkling.detainment.scheduler
import java.net.URL
import java.nio.file.Files

private val url = "https://github.com/stephengold/Libbulletjme/releases/download/20.0.0/Linux64DebugSp_libbulletjme.so"
val bulletFile = instance.dataFolder.resolve("Linux64DebugSp_libbulletjme.so")

fun downloadNativeLibrary(callback: () -> Unit) {
    instance.dataFolder.mkdir()

    if (bulletFile.exists()) {
        scheduler.runTask(instance, callback)
        return
    }

    URL(url).openStream().use { Files.copy(it, bulletFile.toPath()) }
    instance.logger.info("Downloaded the native library.")

    scheduler.runTask(instance, callback)
}