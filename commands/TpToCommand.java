package me.ckanto.core.commands;

import me.ckanto.core.CkantoCore;
import me.ckanto.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpToCommand implements CommandExecutor {

    private final CkantoCore plugin;

    public TpToCommand(CkantoCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("ckanto.tpto")) {
            MessageUtil.send(sender, "no-permission");
            return true;
        }

        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "player-only");
            return true;
        }

        if (args.length == 0) {
            MessageUtil.sendRaw(sender, "tpto.usage");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            MessageUtil.sendRaw(sender, "messages.player-not-found", "{player}", args[0]);
            return true;
        }

        if (target.equals(player)) {
            MessageUtil.sendRaw(sender, "messages.no-permission");
            return true;
        }

        String fromName = player.getName();
        player.teleport(target.getLocation());
        MessageUtil.sendRaw(sender, "tpto.teleported", "{from}", fromName, "{to}", target.getName());
        return true;
    }
}
