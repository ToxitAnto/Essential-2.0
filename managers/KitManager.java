package me.ckanto.core.managers;

import me.ckanto.core.CkantoCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class KitManager {

    private final CkantoCore plugin;
    private final File kitsFile;
    private final File cooldownFile;
    private FileConfiguration kitsData;
    private FileConfiguration cooldownData;

    private final Map<String, KitData> kits = new HashMap<>();
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public record KitData(List<ItemStack> items, long cooldownSeconds) {}

    public KitManager(CkantoCore plugin) {
        this.plugin = plugin;
        this.kitsFile = new File(plugin.getDataFolder(), "kits.yml");
        this.cooldownFile = new File(plugin.getDataFolder(), "cooldowns.yml");
        load();
    }

    @SuppressWarnings("unchecked")
    private void load() {
        kitsFile.getParentFile().mkdirs();
        if (!kitsFile.exists()) {
            try { kitsFile.createNewFile(); } catch (IOException e) { plugin.getLogger().severe("Impossibile creare kits.yml"); }
        }
        if (!cooldownFile.exists()) {
            try { cooldownFile.createNewFile(); } catch (IOException e) { plugin.getLogger().severe("Impossibile creare cooldowns.yml"); }
        }

        kitsData = YamlConfiguration.loadConfiguration(kitsFile);
        cooldownData = YamlConfiguration.loadConfiguration(cooldownFile);

        if (kitsData.getConfigurationSection("kits") != null) {
            for (String key : kitsData.getConfigurationSection("kits").getKeys(false)) {
                List<?> raw = kitsData.getList("kits." + key + ".items");
                long cd = kitsData.getLong("kits." + key + ".cooldown", 0);
                if (raw != null) {
                    List<ItemStack> items = new ArrayList<>();
                    for (Object obj : raw) {
                        if (obj instanceof ItemStack is) items.add(is);
                    }
                    kits.put(key.toLowerCase(), new KitData(items, cd));
                }
            }
        }

        if (cooldownData.getConfigurationSection("cooldowns") != null) {
            for (String uuidStr : cooldownData.getConfigurationSection("cooldowns").getKeys(false)) {
                try {
                    UUID uuid = UUID.fromString(uuidStr);
                    Map<String, Long> map = new HashMap<>();
                    if (cooldownData.getConfigurationSection("cooldowns." + uuidStr) != null) {
                        for (String kitName : cooldownData.getConfigurationSection("cooldowns." + uuidStr).getKeys(false)) {
                            map.put(kitName, cooldownData.getLong("cooldowns." + uuidStr + "." + kitName));
                        }
                    }
                    cooldowns.put(uuid, map);
                } catch (IllegalArgumentException ignored) {}
            }
        }
    }

    public void save() {
        kits.forEach((name, data) -> {
            kitsData.set("kits." + name + ".items", data.items());
            kitsData.set("kits." + name + ".cooldown", data.cooldownSeconds());
        });
        cooldowns.forEach((uuid, map) ->
            map.forEach((kit, time) ->
                cooldownData.set("cooldowns." + uuid + "." + kit, time)
            )
        );
        try {
            kitsData.save(kitsFile);
            cooldownData.save(cooldownFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Impossibile salvare i dati dei kit.");
        }
    }

    public boolean createKit(String name, List<ItemStack> items, long cooldownSeconds) {
        if (kits.containsKey(name.toLowerCase())) return false;
        kits.put(name.toLowerCase(), new KitData(new ArrayList<>(items), cooldownSeconds));
        save();
        return true;
    }

    public boolean deleteKit(String name) {
        if (!kits.containsKey(name.toLowerCase())) return false;
        kits.remove(name.toLowerCase());
        kitsData.set("kits." + name.toLowerCase(), null);
        save();
        return true;
    }

    public KitData getKit(String name) {
        return kits.get(name.toLowerCase());
    }

    public Set<String> getKits() {
        return kits.keySet();
    }

    public boolean exists(String name) {
        return kits.containsKey(name.toLowerCase());
    }

    public long getRemainingCooldown(Player player, String kitName) {
        Map<String, Long> map = cooldowns.get(player.getUniqueId());
        if (map == null) return 0;
        Long lastUse = map.get(kitName.toLowerCase());
        if (lastUse == null) return 0;
        KitData data = kits.get(kitName.toLowerCase());
        if (data == null) return 0;
        long remaining = (lastUse + data.cooldownSeconds() * 1000L) - System.currentTimeMillis();
        return Math.max(0, remaining / 1000L);
    }

    public void setCooldown(Player player, String kitName) {
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                 .put(kitName.toLowerCase(), System.currentTimeMillis());
    }
}
