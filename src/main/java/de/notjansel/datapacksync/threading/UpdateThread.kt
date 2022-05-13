package de.notjansel.datapacksync.threading

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.notjansel.datapacksync.Datapacksync
import de.notjansel.datapacksync.enums.VersionTypes
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
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
            VersionTypes.BETA -> { beta_channel() }
        }

        commandSender.sendMessage(ChatColor.GREEN.toString() + "You are already running the latest version of Datapacksync.")

    }

    private fun beta_channel() {
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val version = obj["beta.latest"]
        if (version == null) {
            commandSender.sendMessage(ChatColor.RED.toString() + "There are no Betas Available. Please Change your Update channel with /updatechannel <Update channel>")
        }
        if (version.asString != Datapacksync.version) {
            try {
                Datapacksync.downloadFile(
                    "https://github.com/TornRPG/datasync/releases/download/$version/datapacksync-$version.jar",
                    Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar"
                )
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            commandSender.sendMessage(ChatColor.GOLD.toString() + "Datapacksync will use version " + version + " and remove the old file on the next reload/restart (restart recommended if you have other plugins as well)")
            return;
        } else {
            commandSender.sendMessage(ChatColor.GREEN.toString() + "You are already on the latest beta.")
        }
    }

    private fun release_candidate_channel() {
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val version = obj["release_candidate.latest"]
        if (version == null) {
            commandSender.sendMessage(ChatColor.RED.toString() + "There are no Release Candidates Available. Please Change your Update channel with /updatechannel <Update channel>")
        }
        if (version.asString != Datapacksync.version) {
            try {
                Datapacksync.downloadFile(
                    "https://github.com/TornRPG/datasync/releases/download/$version/datapacksync-$version.jar",
                    Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar"
                )
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            commandSender.sendMessage(ChatColor.GOLD.toString() + "Datapacksync will use version " + version + " and remove the old file on the next reload/restart (restart recommended if you have other plugins as well)")
            return;
        } else {
            commandSender.sendMessage(ChatColor.GREEN.toString() + "You are already on the latest beta.")
        }
    }

    fun release_channel() {
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val version = obj["release.latest"]
        if (version.asString != Datapacksync.version) {
            try {
                Datapacksync.downloadFile(
                    "https://github.com/TornRPG/datasync/releases/download/$version/datapacksync-$version.jar",
                    Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar"
                )
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            commandSender.sendMessage(ChatColor.GOLD.toString() + "Datapacksync will use version " + version + " and remove the old file on the next reload/restart (restart recommended if you have other plugins as well)")
            return
        } else {
            commandSender.sendMessage(ChatColor.GREEN.toString() + "You are already on the latest release.")
        }
    }
}