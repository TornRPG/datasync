package de.notjansel.datapacksync.commands

import de.notjansel.datapacksync.threading.DatapackThread
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Download : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val thread = DatapackThread(sender, args)
        thread.start()
        return true
    }
}