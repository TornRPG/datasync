package de.notjansel.datapacksync.commands

import de.notjansel.datapacksync.threading.UpdateThread
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Update : CommandExecutor {
    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        if (!commandSender.hasPermission("datapacksync.use")) {
            commandSender.sendMessage("You do not have the Permission to use this.")
            return true
        }
        val thread = UpdateThread(commandSender)
        thread.start()
        return true
    }
}