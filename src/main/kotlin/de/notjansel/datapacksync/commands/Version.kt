package de.notjansel.datapacksync.commands

import de.notjansel.datapacksync.Datapacksync
import de.notjansel.datapacksync.Datapacksync.Companion.version
import de.notjansel.datapacksync.Datapacksync.Companion.versiontype
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Version : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<String>): Boolean {
        sender.sendMessage("""
        ${ChatColor.YELLOW}DatapackSync
        ${ChatColor.YELLOW}Version: ${ChatColor.GREEN}${version}
        ${ChatColor.YELLOW}Version Type: ${ChatColor.BLUE}${versiontype.toString()}
        ${ChatColor.YELLOW}Author: ${ChatColor.GREEN}NotJansel
        ${ChatColor.YELLOW}Github Repository: ${ChatColor.RED}https://github.com/TornRPG/datasync
        ${ChatColor.YELLOW}Minecraft Version: ${ChatColor.GREEN}${Datapacksync.mcversion}
        """)
        return true
    }
}