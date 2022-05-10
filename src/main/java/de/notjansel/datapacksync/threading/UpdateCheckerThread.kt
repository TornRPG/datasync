package de.notjansel.datapacksync.threading

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.notjansel.datapacksync.Datapacksync
import org.bukkit.Bukkit
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class UpdateCheckerThread : Runnable {
    override fun run() {
        try {
            Datapacksync.downloadFile("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json", Datapacksync.serverpath + "/downloads/version.json")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val version = obj["latest"].asString
        val changelog = obj["changelog"].asString
        if (Datapacksync.version.endsWith("-dev")) {
            return;
        }
        if (version != Datapacksync.version && !Datapacksync.version.endsWith("-dev")) {
            for (player in Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("datapacksync.use")) {
                    player.sendMessage("""§cDatapackSync Update Available. Run /update to Update the Plugin.
                            Changelog for Version $version: $changelog
                            """);
                }
            }
        }
    }
}