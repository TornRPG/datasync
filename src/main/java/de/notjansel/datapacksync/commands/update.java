package de.notjansel.datapacksync.commands;

import de.notjansel.datapacksync.Datapacksync;
import de.notjansel.datapacksync.threading.DownloadThread;
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
        if(!commandSender.hasPermission("datapacksync.use")) {
            commandSender.sendMessage("You do not have the Permission to use this.");
            return true;
        }
        DownloadThread thread = new DownloadThread(commandSender);
        thread.start();
        return true;
    }
}
