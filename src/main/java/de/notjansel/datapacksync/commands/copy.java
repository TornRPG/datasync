package de.notjansel.datapacksync.commands;

import de.notjansel.datapacksync.Datapacksync;
import de.notjansel.datapacksync.threading.CopyThread;
import io.papermc.paper.datapack.Datapack;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.*;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static de.notjansel.datapacksync.Datapacksync.*;

public class copy implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CopyThread thread = new CopyThread(sender, args);
        thread.start();
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
