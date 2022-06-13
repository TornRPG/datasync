package de.notjansel.datapacksync

import de.notjansel.datapacksync.commands.*
import de.notjansel.datapacksync.listeners.JoinListener
import de.notjansel.datapacksync.threading.UpdateCheckerThread
import de.notjansel.datapacksync.enums.VersionTypes
import de.notjansel.datapacksync.inventories.ConfigInv
import de.notjansel.datapacksync.listeners.InventoryListener
import io.papermc.paper.datapack.DatapackManager
import org.apache.commons.io.FileUtils
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.net.URL

class Datapacksync : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(JoinListener(), this)
        server.pluginManager.registerEvents(InventoryListener(), this)
        getCommand("datasync")!!.setExecutor(Datasync())
        config.addDefault("datasync.update_channel", VersionTypes.RELEASE)
        config.addDefault("datasync.auto_check", true)
        saveDefaultConfig()
        configfile = config
        serverpath = server.worldContainer.absolutePath.replace(".", "")
        worlds = server.worlds
        datapackManager = server.datapackManager
        Companion.server = server
        plugininstance = this
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, UpdateCheckerThread(), 1200, 18000)
        try {
            prepareRequisites()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        remorselessnessAgainstFiles()
        ConfigInv.GetConfigInv()
    }

    private fun remorselessnessAgainstFiles() {
        for (file in File(serverpath + "/plugins").listFiles()!!) {
            if (file.name.contains("datapacksync")) {
                if (!file.name.contains(version)) {
                    file.delete()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun prepareRequisites() {
        if (!File("$serverpath/downloads/version.json").exists()) {
            val file = File(serverpath + "downloads/version.json")
            val fetchsite = URL("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json")
            FileUtils.copyURLToFile(fetchsite, file)
        }
    }

    override fun onDisable() {
        configfile.save(File("$serverpath/plugins/Datapacksync/config.yml"))
    }

    companion object {
        var serverpath: String? = null
        var worlds: List<World>? = null
        var datapackManager: DatapackManager? = null
        var server: Server? = null
        var versiontype: VersionTypes = VersionTypes.RELEASE;
        lateinit var configfile: FileConfiguration
        lateinit var plugininstance: Plugin
        const val version = "0.31.0"

        @Throws(IOException::class)
        fun downloadFile(url: String?, path: String?) {
            val file = File(path)
            val fetchsite = URL(url)
            FileUtils.copyURLToFile(fetchsite, file)
        }
    }
}