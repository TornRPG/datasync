package de.notjansel.datapacksync.commands;

import de.notjansel.datapacksync.Datapacksync;
import io.papermc.paper.datapack.Datapack;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static de.notjansel.datapacksync.Datapacksync.*;

public class copy implements CommandExecutor, TabCompleter {
    FileInputStream fis = null;
    FileOutputStream fos = null;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("datapacksync.use")) {
            sender.sendMessage("You do not have the Permission to use this.");
            return true;
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
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            sender.sendMessage(ChatColor.GREEN + "Copy complete. To enable, first /datapack list, so the Server pickups the Datapack, then /datapack enable <Datapack>");
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tabComplete = new ArrayList<>();
        if (args.length == 1) {
            File directoryPath = new File(Datapacksync.serverpath + "/downloads/");
            for (String filename : directoryPath.list()
                 ) {
                tabComplete.add(filename);
            }
        }


        return tabComplete;
    }
}
