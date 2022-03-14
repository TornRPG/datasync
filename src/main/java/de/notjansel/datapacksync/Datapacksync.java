package de.notjansel.datapacksync;

import de.notjansel.datapacksync.commands.copy;
import de.notjansel.datapacksync.commands.download;
import de.notjansel.datapacksync.commands.update;
import de.notjansel.datapacksync.listeners.JoinListener;
import io.papermc.paper.datapack.DatapackManager;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public final class Datapacksync extends JavaPlugin {

    public static String serverpath;
    public static List<World> worlds;
    public static DatapackManager datapackManager;
    public static Server server;
    public static String version = "0.25.2";

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getCommand("copy").setExecutor(new copy());
        getCommand("download").setExecutor(new download());
        getCommand("update").setExecutor(new update());
        serverpath = getServer().getWorldContainer().getAbsolutePath();
        worlds = getServer().getWorlds();
        datapackManager = getServer().getDatapackManager();
        server = getServer();
        removeoldfiles();
    }

    private void removeoldfiles() {
        for (File file : new File(serverpath + "/plugins").listFiles()) {
            if (file.getName().contains("datapacksync")) {
                if (!file.getName().contains(version)) {
                    file.delete();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
