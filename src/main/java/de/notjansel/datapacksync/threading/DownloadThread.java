package de.notjansel.datapacksync.threading;

import de.notjansel.datapacksync.Datapacksync;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DownloadThread extends Thread{

    private CommandSender commandSender;

    public DownloadThread(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public void run() {
        commandSender.sendMessage("Starting update... (The server may lag)");
        InputStream inputStream = null;
        try {
            inputStream = new URL("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json").openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.copy(inputStream, Paths.get(Datapacksync.serverpath + "/downloads/version.json"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object obj;
        try {
            obj = new JSONParser().parse(Files.readString(Paths.get(Datapacksync.serverpath + "/downloads/version.json")));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
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
            }
            try {
                Files.copy(inputStream, Paths.get(Datapacksync.serverpath + "/plugins/datapacksync-" + version + ".jar"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            commandSender.sendMessage(ChatColor.GOLD + "Reloading Server to update Datapacksync to version " + version + " and remove the old file.");
            Bukkit.getServer().reload();
        } else {
            commandSender.sendMessage(ChatColor.GREEN + "You are already running the latest version of Datapacksync.");
        }
    }

}
