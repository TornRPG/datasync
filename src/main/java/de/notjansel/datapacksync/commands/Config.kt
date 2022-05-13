package de.notjansel.datapacksync.commands

import de.notjansel.datapacksync.inventories.ConfigInv
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
        if (sender !is Player) {
            sender.sendMessage("This command has to be executed as player, as it opens a GUI")
            return true;
        }
        sender.openInventory(ConfigInv.inv)
        return true
    }

}