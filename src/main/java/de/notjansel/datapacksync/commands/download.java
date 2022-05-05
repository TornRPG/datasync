package de.notjansel.datapacksync.commands;

import de.notjansel.datapacksync.Datapacksync;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class download implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("datapacksync.use")) {
            sender.sendMessage("You do not have the Permission to use this.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("No Filename and URL given, aborting...");
            sender.sendMessage("Syntax: /sync <filename> <url>");
            return true;
        }
        if (args[0].equals("debug")) {
            sender.sendMessage(Datapacksync.serverpath);
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage("No Url given, aborting...");
            sender.sendMessage("Syntax: /sync <filename> <url>");
            return true;
        }
        try {
            down(args[1], args[0], sender);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void down(String url, String filename, CommandSender sender) throws IOException {
        Path dirPath = Paths.get(Datapacksync.serverpath + "/downloads/");
        try {
            if (!Files.exists(dirPath)) {
                Files.createDirectory(dirPath);
                Bukkit.getConsoleSender().sendMessage("Directory Created");
            }
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("Error while Creating Directory:" + e.getMessage());
        }
        InputStream inputStream = new URL(url).openStream();
        Files.copy(inputStream, Paths.get(Datapacksync.serverpath + "/downloads/" + filename), StandardCopyOption.REPLACE_EXISTING);
        sender.sendMessage(ChatColor.GREEN + "Download complete. To copy the Datapack, type /copy <Filename>");

    }
}
