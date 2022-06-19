package de.notjansel.datapacksync.listeners

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.notjansel.datapacksync.Datapacksync
import de.notjansel.datapacksync.enums.VersionTypes
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
            lateinit var version: String;
            lateinit var changelog: String;
            when (Datapacksync.configfile.get("datasync.update_channel")) {
                VersionTypes.RELEASE.name -> {
                    version = obj.get("release").asJsonObject.get("latest").asString
                    changelog = obj.get("release").asJsonObject.get("changelog").asString
                }
                VersionTypes.BETA.name -> {
                    version = obj.get("beta").asJsonObject.get("latest").asString
                    changelog = obj.get("beta").asJsonObject.get("changelog").asString
                }
                VersionTypes.RELEASE_CANDIDATE.name -> {
                    version = obj.get("release_candidate").asJsonObject.get("latest").asString
                    changelog = obj.get("release_candidate").asJsonObject.get("changelog").asString
                }
            }
            if (Datapacksync.version.endsWith("-dev")) {
                event.player.sendMessage(ChatColor.AQUA.toString() + "You are running a development version of Datapacksync. Please run a Stable version to get Support. Current Stable Release: $version")
                return;
            }
            if (version != Datapacksync.version && !Datapacksync.version.endsWith("-dev")) {
                event.player.sendMessage("""
    ${ChatColor.GREEN}There's a new version of DatapackSync available!
    ${ChatColor.GOLD}To Update, run /update
    Changelog for Version $version: $changelog
    """.trimIndent())
            }
        }
    }
}