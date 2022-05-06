package de.notjansel.datapacksync.threading;

import de.notjansel.datapacksync.Datapacksync;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static de.notjansel.datapacksync.Datapacksync.worlds;

public class CopyThread extends Thread {
    private final CommandSender sender;
    private final String[] args;
    FileInputStream fis = null;
    FileOutputStream fos = null;

    public CopyThread(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void run() {
        if(!sender.hasPermission("datapacksync.use")) {
            sender.sendMessage("You do not have the Permission to use this.");
            return;
        }
        String datapackpath = "";
        for (World world : worlds) {
            if (Files.exists(Paths.get(world.getWorldFolder().getAbsolutePath() + "/datapacks/"))) {
                datapackpath = world.getWorldFolder().getAbsolutePath() + "/datapacks/";
            }
        }
        try {
            fis = new FileInputStream(Datapacksync.serverpath + "/downloads/" + args[0]);

            fos = new FileOutputStream(datapackpath + args[0]);
            int c;
            while ((c = fis.read()) != -1) {
                fos.write(c);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }
            sender.sendMessage(ChatColor.GREEN + "Copy complete. To enable, first /datapack list, so the Server pickups the Datapack, then /datapack enable <Datapack>");
        }

    }
}
