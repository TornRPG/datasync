package de.notjansel.datapacksync.threading

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.notjansel.datapacksync.Datapacksync
import de.notjansel.datapacksync.Datapacksync.Companion.version
import de.notjansel.datapacksync.versioning.VersionTypes
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class UpdateThread(private val commandSender: CommandSender) : Thread() {
    override fun run() {
        val target: Plugin
        commandSender.sendMessage("Starting update... (The server may lag)")
        try {
            Datapacksync.downloadFile("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json", Datapacksync.serverpath + "/downloads/version.json")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        when (Datapacksync.configfile.get("datasync.update_channel")) {
            VersionTypes.RELEASE -> { release_channel() }
            VersionTypes.RELEASE_CANDIDATE -> { release_candidate_channel() }
        }



            commandSender.sendMessage(ChatColor.GREEN.toString() + "You are already running the latest version of Datapacksync.")

    }

    private fun release_candidate_channel() {
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val version = obj["_release_candidatelatest"].asString
        try {
            Datapacksync.downloadFile("https://github.com/TornRPG/datasync/releases/download/$version/datapacksync-$version.jar", Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        commandSender.sendMessage(ChatColor.GOLD.toString() + "Datapacksync will use version " + version + " and remove the old file on the next reload/restart (restart recommended if you have other plugins as well)")

    }

    fun release_channel() {
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val version = obj["latest"].asString
        try {
            Datapacksync.downloadFile("https://github.com/TornRPG/datasync/releases/download/$version/datapacksync-$version.jar", Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        commandSender.sendMessage(ChatColor.GOLD.toString() + "Datapacksync will use version " + version + " and remove the old file on the next reload/restart (restart recommended if you have other plugins as well)")

    }
}