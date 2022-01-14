package de.notjansel.datapacksync;

import de.notjansel.datapacksync.commands.copy;
import de.notjansel.datapacksync.commands.download;
import io.papermc.paper.datapack.DatapackManager;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Datapacksync extends JavaPlugin {

    public static String serverpath;
    public static List<World> worlds;
    public static DatapackManager datapackManager;
    public static Server server;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("copyS").setExecutor(new copy());
        getCommand("download").setExecutor(new download());
        serverpath = getServer().getWorldContainer().getAbsolutePath();
        worlds = getServer().getWorlds();
        datapackManager = getServer().getDatapackManager();
        server = getServer();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
