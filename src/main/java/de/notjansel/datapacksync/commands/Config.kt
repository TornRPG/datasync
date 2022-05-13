package de.notjansel.datapacksync.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

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
        TODO("Not yet implemented")
    }

}