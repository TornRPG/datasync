package de.notjansel.datapacksync.threading;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.notjansel.datapacksync.Datapacksync;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadThread extends Thread{

    private CommandSender commandSender;

    public DownloadThread(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public void run() {
        commandSender.sendMessage("Starting update... (The server may lag)");
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
            try {
                Datapacksync.downloadFile("https://github.com/TornRPG/datasync/releases/download/" + version + "/datapacksync-" + version + ".jar", Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            commandSender.sendMessage(ChatColor.GOLD + "Reloading Server to update Datapacksync to version " + version + " and remove the old file.");
            Bukkit.getServer().reload();
        } else {
            commandSender.sendMessage(ChatColor.GREEN + "You are already running the latest version of Datapacksync.");
        }
    }

}
