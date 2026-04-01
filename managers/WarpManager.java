package me.ckanto.core.managers;

import me.ckanto.core.CkantoCore;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WarpManager {

    private final CkantoCore plugin;
    private final File file;
    private FileConfiguration data;
    private final Map<String, Location> warps = new HashMap<>();

    public WarpManager(CkantoCore plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "warps.yml");
        load();
    }

    private void load() {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Impossibile creare warps.yml");
            }
        }
        data = YamlConfiguration.loadConfiguration(file);
        if (data.getConfigurationSection("warps") != null) {
            for (String key : data.getConfigurationSection("warps").getKeys(false)) {
                Location loc = (Location) data.get("warps." + key);
                if (loc != null) warps.put(key.toLowerCase(), loc);
            }
        }
    }

    public void save() {
        warps.forEach((name, loc) -> data.set("warps." + name, loc));
        try {
            data.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Impossibile salvare warps.yml");
        }
    }

    public boolean setWarp(String name, Location location) {
        if (warps.containsKey(name.toLowerCase())) return false;
        warps.put(name.toLowerCase(), location);
        save();
        return true;
    }

    public boolean deleteWarp(String name) {
        if (!warps.containsKey(name.toLowerCase())) return false;
        warps.remove(name.toLowerCase());
        data.set("warps." + name.toLowerCase(), null);
        save();
        return true;
    }

    public Location getWarp(String name) {
        return warps.get(name.toLowerCase());
    }

    public Set<String> getWarps() {
        return warps.keySet();
    }

    public boolean exists(String name) {
        return warps.containsKey(name.toLowerCase());
    }
}
