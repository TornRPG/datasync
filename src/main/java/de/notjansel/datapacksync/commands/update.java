package de.notjansel.datapacksync.commands;

import de.notjansel.datapacksync.Datapacksync;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class update implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        InputStream inputStream = null;
        try {
            inputStream = new URL("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json").openStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Files.copy(inputStream, Paths.get(Datapacksync.serverpath + "/downloads/version.json"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Object obj;
        try {
            obj = new JSONParser().parse(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json")));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        String version = (String) jsonObject.get("latest");
        if (!version.equals(Datapacksync.version)) {
            try {
                inputStream = new URL("https://github.com/TornRPG/datasync/releases/download/" + version + "/datapacksync-" + version + ".jar").openStream();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            try {
                Files.copy(inputStream, Paths.get(Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            commandSender.sendMessage(ChatColor.GOLD + "Reloading Server to update Datapacksync to version " + version + " and remove the old file.");
            Bukkit.getServer().reload();
        } else {
            commandSender.sendMessage(ChatColor.GREEN + "You are already running the latest version of Datapacksync.");
        }
        return true;
    }
}
