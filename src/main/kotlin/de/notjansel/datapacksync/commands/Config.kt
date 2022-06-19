package de.notjansel.datapacksync.commands

import de.notjansel.datapacksync.inventories.ConfigInv
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class Config : CommandExecutor, TabExecutor {
    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): MutableList<String>? {
        var list: MutableList<String> = ArrayList()
        return list
    }

    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<out String>): Boolean {
        sender.sendMessage("${ChatColor.RED}This command is disabled in this version. Please edit the config manually.")
        return true
    //if (sender !is Player) {
        //    sender.sendMessage("This command has to be executed as player, as it opens a GUI")
        //    sender.sendMessage("Another way to edit the Config is manual.")
        //    return true;
        //}
        //sender.openInventory(ConfigInv.inv)
        //return true
    }

}