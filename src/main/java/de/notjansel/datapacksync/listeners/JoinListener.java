package de.notjansel.datapacksync.listeners;

import de.notjansel.datapacksync.Datapacksync;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException, ParseException {
        if (event.getPlayer().hasPermission("datapacksync.use")) {
            InputStream inputStream = new URL("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json").openStream();
            Files.copy(inputStream, Paths.get(Datapacksync.serverpath + "/downloads/version.json"), StandardCopyOption.REPLACE_EXISTING);
            Object obj;
            obj = new JSONParser().parse(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json")));
            JSONObject jsonObject = (JSONObject) obj;
            String version = (String) jsonObject.get("latest");
            if (!version.equals(Datapacksync.version)) {
                    event.getPlayer().sendMessage(ChatColor.GREEN + "There's a new version of DatapackSync available!\n" + ChatColor.GOLD + "To Update, run /update");
            }
        }
    }
}
