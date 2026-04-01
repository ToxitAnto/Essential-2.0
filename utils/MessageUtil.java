package me.ckanto.core.utils;

import me.ckanto.core.CkantoCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

public class MessageUtil {

    private static CkantoCore plugin;

    public static void init(CkantoCore instance) {
        plugin = instance;
    }

    public static Component colorize(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public static void send(CommandSender sender, String configPath) {
        String prefix = plugin.getConfig().getString("prefix", "&8[&bCkanto&8] &r");
        String msg = plugin.getConfig().getString("messages." + configPath, "&cMessaggio non trovato.");
        sender.sendMessage(colorize(prefix + msg));
    }

    public static void sendRaw(CommandSender sender, String configPath, String... replacements) {
        String prefix = plugin.getConfig().getString("prefix", "&8[&bCkanto&8] &r");
        String msg = plugin.getConfig().getString(configPath, "&cMessaggio non trovato.");
        for (int i = 0; i < replacements.length - 1; i += 2) {
            msg = msg.replace(replacements[i], replacements[i + 1]);
        }
        sender.sendMessage(colorize(prefix + msg));
    }

    public static String formatTime(long seconds) {
        if (seconds < 60) return seconds + "s";
        if (seconds < 3600) return (seconds / 60) + "m " + (seconds % 60) + "s";
        return (seconds / 3600) + "h " + ((seconds % 3600) / 60) + "m";
    }
}
