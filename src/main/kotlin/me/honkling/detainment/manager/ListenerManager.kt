package me.honkling.knockffa.manager

import me.honkling.detainment.lib.getClassesInPackage
import me.honkling.detainment.manager.annotations.Listener
import me.honkling.detainment.pluginManager
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.Listener as BukkitListener

class ListenerManager(val instance: JavaPlugin) {
    fun registerListeners(pkg: String) {
        val listeners = getClassesInPackage(instance::class.java, pkg, ::isListener)

        for (listener in listeners) {
            val listenerInstance = object : BukkitListener {} as BukkitListener

            for (method in listener.declaredMethods) {
                val event = method.parameters.firstOrNull() ?: continue

                if (!method.canAccess(null) || !isEvent(event.type))
                    continue

                @Suppress("UNCHECKED_CAST")
                pluginManager.registerEvent(event.type as Class<out Event>, listenerInstance, EventPriority.NORMAL, EventExecutor { _, evt ->
                    method.invoke(null, evt)
                }, instance)
            }
        }
    }

    private fun isEvent(clazz: Class<*>): Boolean {
        return Event::class.java.isAssignableFrom(clazz)
    }

    private fun isListener(clazz: Class<*>): Boolean {
        return clazz.isAnnotationPresent(Listener::class.java)
    }
}