package de.notjansel.datapacksync.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class Datasync: CommandExecutor, TabExecutor {
    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): MutableList<String>? {
        if (p3.size == 1) {
            val tabComplete: MutableList<String> = ArrayList()
            tabComplete.add("copy")
            tabComplete.add("download")
            tabComplete.add("config")
            tabComplete.add("version")
            tabComplete.add("update")
            return tabComplete
        }
        return null
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(ChatColor.RED.toString() + "Usage: /datasync <command>\nPossible Commands: copy, download, config, version, update")
            return true
        }
        if (args.size >= 1) {
            when (args[0]) {
                "copy" -> {return Copy().onCommand(sender, command, label, args)}
                "download" -> {return Download().onCommand(sender, command, label, args)}
                "config" -> {return Config().onCommand(sender, command, label, args)}
                "version" -> {return Version().onCommand(sender, command, label, args)}
                "update" -> { return Update().onCommand(sender, command, label, args)}
            }
            return true
        }
        return true
    }
}