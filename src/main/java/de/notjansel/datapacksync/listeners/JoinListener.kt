package de.notjansel.datapacksync.listeners

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.notjansel.datapacksync.Datapacksync
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class JoinListener : Listener {
    @EventHandler
    @Throws(IOException::class, ParseException::class)
    fun onJoin(event: PlayerJoinEvent) {
        if (event.player.hasPermission("datapacksync.use")) {
            val inputStream = URL("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json").openStream()
            Files.copy(inputStream, Paths.get(Datapacksync.serverpath + "/downloads/version.json"), StandardCopyOption.REPLACE_EXISTING)
            val obj: JsonObject = try {
                JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            val version = obj["latest"].asString
            val changelog = obj["changelog"].asString
            if (version != Datapacksync.version) {
                event.player.sendMessage("""
    ${ChatColor.GREEN}There's a new version of DatapackSync available!
    ${ChatColor.GOLD}To Update, run /update
    Changelog for Version $version: $changelog
    """.trimIndent())
            }
        }
    }
}