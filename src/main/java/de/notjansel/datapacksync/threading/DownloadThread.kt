package de.notjansel.datapacksync.threading

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.notjansel.datapacksync.Datapacksync
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class DownloadThread(private val commandSender: CommandSender) : Thread() {
    override fun run() {
        commandSender.sendMessage("Starting update... (The server may lag)")
        try {
            Datapacksync.downloadFile("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json", Datapacksync.serverpath + "/downloads/version.json")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val version = obj["latest"].asString
        if (version != Datapacksync.version) {
            try {
                Datapacksync.downloadFile("https://github.com/TornRPG/datasync/releases/download/$version/datapacksync-$version.jar", Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar")
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            commandSender.sendMessage(ChatColor.GOLD.toString() + "Reloading Server to update Datapacksync to version " + version + " and remove the old file.")
            Bukkit.getServer().reload()
        } else {
            commandSender.sendMessage(ChatColor.GREEN.toString() + "You are already running the latest version of Datapacksync.")
        }
    }
}