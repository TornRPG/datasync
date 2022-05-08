package de.notjansel.datapacksync

import de.notjansel.datapacksync.commands.Copy
import de.notjansel.datapacksync.commands.Download
import de.notjansel.datapacksync.commands.Update
import de.notjansel.datapacksync.listeners.JoinListener
import de.notjansel.datapacksync.threading.UpdateCheckerThread
import io.papermc.paper.datapack.DatapackManager
import org.apache.commons.io.FileUtils
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.net.URL

class Datapacksync : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        server.pluginManager.registerEvents(JoinListener(), this)
        getCommand("copy")!!.setExecutor(Copy())
        getCommand("Download")!!.setExecutor(Download())
        getCommand("update")!!.setExecutor(Update())
        serverpath = server.worldContainer.absolutePath.replace(".", "")
        worlds = server.worlds
        datapackManager = server.datapackManager
        Companion.server = server
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, UpdateCheckerThread(), 1200, 18000)
        try {
            prepare_requisites()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        remorselessness_against_files()
    }

    private fun remorselessness_against_files() {
        for (file in File(serverpath + "/plugins").listFiles()) {
            if (file.name.contains("datapacksync")) {
                if (!file.name.contains(version)) {
                    file.delete()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun prepare_requisites() {
        if (!File(serverpath + "/downloads/version.json").exists()) {
            val file = File(serverpath + "downloads/version.json")
            val fetchsite = URL("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json")
            FileUtils.copyURLToFile(fetchsite, file)
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        var serverpath: String? = null
        var worlds: List<World>? = null
        var datapackManager: DatapackManager? = null
        var server: Server? = null
        const val version = "0.30.3"
        @Throws(IOException::class)
        fun downloadFile(url: String?, path: String?) {
            val file = File(path)
            val fetchsite = URL(url)
            FileUtils.copyURLToFile(fetchsite, file)
        }
    }
}