package de.notjansel.datapacksync.threading;

import de.notjansel.datapacksync.Datapacksync;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class DatapackThread extends Thread {
    private final String[] args;
    private final CommandSender sender;
    public DatapackThread(CommandSender sender, String[] args) {
        this.args = args;
        this.sender = sender;
    }

    @Override
    public void run() {
        if (!sender.hasPermission("datapacksync.use")) {
            sender.sendMessage("You do not have the Permission to use this.");
            return;
        }
        if (args.length == 0) {
            sender.sendMessage("No Filename and URL given, aborting...");
            sender.sendMessage("Syntax: /sync <filename> <url>");
            return;
        }
        if (args[0].equals("debug")) {
            sender.sendMessage(Datapacksync.serverpath);
            return;
        }
        if (args.length == 1) {
            sender.sendMessage("No Url given, aborting...");
            sender.sendMessage("Syntax: /sync <filename> <url>");
            return;
        }
        try {
            Datapacksync.downloadFile(args[1], Datapacksync.serverpath + "/downloads/" + args[0]);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        sender.sendMessage("Download complete. To copy the Datapack, type /copy <Filename>");
    }
}
