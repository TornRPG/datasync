package de.notjansel.datapacksync.commands

import de.notjansel.datapacksync.Datapacksync
import de.notjansel.datapacksync.threading.CopyThread
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.io.File

class Copy : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val thread = CopyThread(sender, args)
        thread.start()
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String>? {
        val tabComplete: MutableList<String> = ArrayList()
        if (args.size == 1) {
            val directoryPath = File(Datapacksync.serverpath + "/downloads/")
            for (filename in directoryPath.list()) {
                tabComplete.add(filename)
            }
        }
        return tabComplete
    }
}