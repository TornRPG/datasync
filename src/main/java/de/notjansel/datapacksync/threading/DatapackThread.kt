package de.notjansel.datapacksync.threading

import de.notjansel.datapacksync.Datapacksync
import org.bukkit.command.CommandSender
import java.io.IOException

class DatapackThread(private val sender: CommandSender, private val args: Array<String>) : Thread() {
    override fun run() {
        if (!sender.hasPermission("datapacksync.use")) {
            sender.sendMessage("You do not have the Permission to use this.")
            return
        }
        if (args.isEmpty()) {
            sender.sendMessage("No Filename and URL given, aborting...")
            sender.sendMessage("Syntax: /sync <filename> <url>")
            return
        }
        if (args[0] == "debug") {
            sender.sendMessage(Datapacksync.serverpath!!)
            return
        }
        if (args.size == 1) {
            sender.sendMessage("No Url given, aborting...")
            sender.sendMessage("Syntax: /sync <filename> <url>")
            return
        }
        try {
            Datapacksync.downloadFile(args[1], Datapacksync.serverpath + "/downloads/" + args[0])
        } catch (e: IOException) {
            throw RuntimeException()
        }
        sender.sendMessage("Download complete. To Copy the Datapack, type /Copy <Filename>")
    }
}