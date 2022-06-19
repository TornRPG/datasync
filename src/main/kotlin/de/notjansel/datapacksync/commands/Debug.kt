package de.notjansel.datapacksync.commands

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.notjansel.datapacksync.Datapacksync
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Debug : CommandExecutor, TabExecutor {
    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>?
    ): MutableList<String>? {
        var arglist = p3?.toMutableList()
        arglist?.removeAt(0)
        if (arglist != null) {
            if (arglist.size == 1) {
                val tabComplete: MutableList<String> = ArrayList()
                tabComplete.add("version")
                tabComplete.add("plugin")
                tabComplete.add("pluginversion")
                tabComplete.add("pluginauthor")
                tabComplete.add("serverpath")
                tabComplete.add("versionfile")
                return tabComplete
            }
        }
        return null
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args == null) {
            sender.sendMessage("Please give a value to retrieve from the code ${ChatColor.RED}(DEBUG PURPOSE ONLY, USE AT UR OWN RISK!)")
            return true
        }
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        var arglist = args.toMutableList()
        arglist.removeAt(0)
        when (arglist[0]) {
            "version" -> {
                sender.sendMessage("Version: ${ChatColor.RED}${Datapacksync.version}")
            }
            "plugin" -> {
                sender.sendMessage("Plugin: ${ChatColor.RED}${Datapacksync.plugininstance.name}")
            }
            "pluginversion" -> {
                sender.sendMessage("Plugin Version: ${ChatColor.RED}${Datapacksync.plugininstance.description.version}")
            }
            "pluginauthor" -> {
                sender.sendMessage("Plugin Author: ${ChatColor.RED}${Datapacksync.plugininstance.description.authors[0]}")
            }
            "serverpath" -> {
                sender.sendMessage("Server Path: ${ChatColor.RED}${Datapacksync.serverpath}")
            }
            "versionfile" -> {
                sender.sendMessage("Version File:")
                sender.sendMessage("${ChatColor.RED}Latest Release: ${obj.get("release").asJsonObject.get("latest").asString}")
            }
        }
        return true;
    }
}