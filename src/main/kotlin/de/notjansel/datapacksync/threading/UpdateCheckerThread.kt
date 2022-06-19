package de.notjansel.datapacksync.threading

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.notjansel.datapacksync.Datapacksync
import de.notjansel.datapacksync.enums.VersionTypes
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class UpdateCheckerThread : Runnable {
    override fun run() {
        if (!Datapacksync.configfile.getBoolean("datasync.auto_check")) {
            return
        }
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
        if (Datapacksync.version.endsWith("-dev")) {
            return;
        }
        when (Datapacksync.configfile.get("datasync.update_channel")) {
            VersionTypes.RELEASE -> { releaseChannel() }
            VersionTypes.RELEASE_CANDIDATE -> { releaseCandidateChannel() }
            VersionTypes.BETA -> { betaChannel() }
        }
    }
    private fun betaChannel() {
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val version = obj["beta.latest"]
        val releaseinstead = obj["beta.release_instead"].asBoolean
        if (releaseinstead) {
            releaseChannel()
            return;
        }
        if (version.asString != Datapacksync.version && !releaseinstead) {
            try {
                Datapacksync.downloadFile(
                    "https://github.com/TornRPG/datasync/releases/download/$version/datapacksync-$version.jar",
                    Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar"
                )
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            return;
        } else {

        }
    }

    private fun releaseCandidateChannel() {
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val version = obj["release_candidate.latest"]
        val releaseinstead = obj["release_candidate.release_instead"].asBoolean
        if (version.asString != Datapacksync.version && !releaseinstead) {
            try {
                Datapacksync.downloadFile(
                    "https://github.com/TornRPG/datasync/releases/download/$version/datapacksync-$version.jar",
                    Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar"
                )
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            return;
        } else {

        }
    }

    private fun releaseChannel() {
        val obj: JsonObject = try {
            JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).asJsonObject
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val version = obj["release.latest"]
        if (version.asString != Datapacksync.version) {
            try {
                Datapacksync.downloadFile(
                    "https://github.com/TornRPG/datasync/releases/download/$version/datapacksync-$version.jar",
                    Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar"
                )
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            return
        } else {
        }
    }
}