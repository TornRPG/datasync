package de.notjansel.datapacksync.threading;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.notjansel.datapacksync.Datapacksync;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UpdateCheckerThread implements Runnable {
    @Override
    public void run() {
        try {
            Datapacksync.downloadFile("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json", Datapacksync.serverpath + "/downloads/version.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonObject obj;
        try {
            obj = JsonParser.parseString(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json"))).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String version = obj.get("latest").getAsString();
        if (!version.equals(Datapacksync.version)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("datapacksync.use")) {
                    player.sendMessage("§cDatapackSync Update Available. Run /update to Update the Plugin.");
                }
            }
        }
    }
}
