package de.notjansel.datapacksync;

import de.notjansel.datapacksync.commands.copy;
import de.notjansel.datapacksync.commands.download;
import de.notjansel.datapacksync.commands.update;
import de.notjansel.datapacksync.listeners.JoinListener;
import io.papermc.paper.datapack.DatapackManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.List;

public final class Datapacksync extends JavaPlugin {

    public static String serverpath;
    public static List<World> worlds;
    public static DatapackManager datapackManager;
    public static Server server;
    public static String version = "0.30.0";

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getCommand("copy").setExecutor(new copy());
        getCommand("download").setExecutor(new download());
        getCommand("update").setExecutor(new update());
        serverpath = getServer().getWorldContainer().getAbsolutePath().replace(".", "");
        worlds = getServer().getWorlds();
        datapackManager = getServer().getDatapackManager();
        server = getServer();
        try {
            prepare_requisites();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void prepare_requisites() throws MalformedURLException, IOException {
        if (!(new File(serverpath + "/downloads/version.json").exists())) {
            File file = new File(serverpath + "downloads/version.json");
            URL fetchsite = new URL("https://raw.githubusercontent.com/TornRPG/datasync/master/version.json");
            FileUtils.copyURLToFile(fetchsite, file);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
