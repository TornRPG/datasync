package de.notjansel.datapacksync.commands

import de.notjansel.datapacksync.Datapacksync.Companion.version
import de.notjansel.datapacksync.Datapacksync.Companion.versiontype
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Version : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<String>): Boolean {
        sender.sendMessage("""${ChatColor.YELLOW}DatapackSync
            Version: $version
            Version Type: ${versiontype.toString()}
            Author: NotJansel
        """.trimIndent())
        return true
    }
}