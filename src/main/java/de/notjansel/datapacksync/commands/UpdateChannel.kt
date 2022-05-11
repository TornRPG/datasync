package de.notjansel.datapacksync.commands

import de.notjansel.datapacksync.Datapacksync
import de.notjansel.datapacksync.versioning.VersionTypes
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import java.io.File
import javax.xml.crypto.Data

class UpdateChannel : CommandExecutor, TabExecutor {
    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): MutableList<String>? {
        var channels: MutableList<String> = ArrayList()
        channels.add(VersionTypes.RELEASE.toString())
        channels.add(VersionTypes.RELEASE_CANDIDATE.toString())
        channels.add(VersionTypes.BETA.toString())
        return channels
    }

    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + "Your current channel is: ${ChatColor.BLUE}${Datapacksync.configfile.get("datasync.update_channel")}")
            return true
        }
        when (args[0]) {
            VersionTypes.RELEASE.toString() -> {Datapacksync.configfile.set("datasync.update_channel", VersionTypes.RELEASE.name)}
            VersionTypes.RELEASE_CANDIDATE.toString() -> {Datapacksync.configfile.set("datasync.update_channel", VersionTypes.RELEASE_CANDIDATE.name)}
            VersionTypes.BETA.toString() -> {Datapacksync.configfile.set("datasync.update_channel", VersionTypes.BETA.name)}
        }
        Datapacksync.configfile.save(File(Datapacksync.serverpath + "/plugins/Datapacksync/config.yml"))
        sender.sendMessage(ChatColor.GREEN.toString() + "Update Channel was set successfully.")
        return true
    }
}