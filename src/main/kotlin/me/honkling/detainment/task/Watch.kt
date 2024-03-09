package me.honkling.detainment.task

import me.honkling.detainment.instance
import me.honkling.detainment.lib.mm
import me.honkling.detainment.lib.propercase
import me.honkling.detainment.schedule.*
import me.honkling.detainment.scheduler
import me.honkling.detainment.world
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.joml.Math.clamp

object Watch : Task {
    val bossbar = BossBar.bossBar(Component.empty(), 0f, BossBar.Color.WHITE, BossBar.Overlay.PROGRESS)
    private var period = Period.ROLL_CALL
    private var periodHour = 6
    var timer = 6 * 60

    override fun schedule() {
        world.time = timer / 60L * 1000
        Bukkit.getOnlinePlayers().forEach { it.showBossBar(bossbar) } // for reloads
        scheduler.scheduleSyncRepeatingTask(instance, ::execute, 0L, 20L)
    }

    override fun execute() {
        val hour = timer.getHours()
        val minutes = timer.getMinutes()

        if (hour in schedule && minutes == 0) {
            period = schedule[hour] ?: period
            periodHour = hour
        }

        var periodHour = periodHour
        val nextPeriod = schedule.keys.filter { it > hour }.minOrNull() ?: schedule.keys.first()
        val elapsed = if (periodHour * 60 > timer) timer + (24 * 60 - periodHour * 60)
                      else timer - periodHour * 60

        // Make sure the hour is less than the next period to avoid negative values in progress clamping to 0
        if (nextPeriod < periodHour)
            periodHour -= 24

        bossbar.name("<b>${period.name.propercase()}</b> <gray>(${timer.getFormattedHours()}:${timer.getFormattedMinutes()})".mm())
        bossbar.progress(clamp(0.0, 1.0, elapsed / (60.0 * (nextPeriod - periodHour))).toFloat())

        timer++

        if (timer >= 24 * 60)
            timer = 0
    }
}