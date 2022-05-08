package de.notjansel.datapacksync.threading

import de.notjansel.datapacksync.Datapacksync
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class CopyThread(private val sender: CommandSender, private val args: Array<String>) : Thread() {
    private var fis: FileInputStream? = null
    private var fos: FileOutputStream? = null
    override fun run() {
        if (!sender.hasPermission("datapacksync.use")) {
            sender.sendMessage("You do not have the Permission to use this.")
            return
        }
        var datapackpath = ""
        for (world in Datapacksync.worlds!!) {
            if (Files.exists(Paths.get(world.worldFolder.absolutePath + "/datapacks/"))) {
                datapackpath = world.worldFolder.absolutePath + "/datapacks/"
            }
        }
        try {
            fis = FileInputStream(Datapacksync.serverpath + "/downloads/" + args[0])
            fos = FileOutputStream(datapackpath + args[0])
            var c: Int
            while (fis!!.read().also { c = it } != -1) {
                fos!!.write(c)
            }
        } catch (e: IOException) {
            throw RuntimeException()
        } finally {
            if (fis != null) {
                try {
                    fis!!.close()
                } catch (e: IOException) {
                    throw RuntimeException()
                }
            }
            if (fos != null) {
                try {
                    fos!!.close()
                } catch (e: IOException) {
                    throw RuntimeException()
                }
            }
            sender.sendMessage(ChatColor.GREEN.toString() + "Copy complete. To enable, first /datapack list, so the Server pickups the Datapack, then /datapack enable <Datapack>")
        }
    }
}