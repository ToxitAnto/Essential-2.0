package me.ckanto.core.commands;

import me.ckanto.core.CkantoCore;
import me.ckanto.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {

    private final CkantoCore plugin;

    public FlyCommand(CkantoCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("ckanto.fly")) {
            MessageUtil.send(sender, "no-permission");
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                MessageUtil.send(sender, "player-only");
                return true;
            }
            toggleFly(sender, player, true);
        } else {
            if (!sender.hasPermission("ckanto.fly.others")) {
                MessageUtil.send(sender, "no-permission");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                MessageUtil.sendRaw(sender, "messages.player-not-found", "{player}", args[0]);
                return true;
            }
            toggleFly(sender, target, sender.equals(target));
        }
        return true;
    }

    private void toggleFly(CommandSender sender, Player target, boolean isSelf) {
        boolean flying = !target.getAllowFlight();
        target.setAllowFlight(flying);
        if (!flying) target.setFlying(false);

        if (isSelf) {
            MessageUtil.sendRaw(sender, flying ? "fly.enabled-self" : "fly.disabled-self");
        } else {
            MessageUtil.sendRaw(sender, flying ? "fly.enabled-other" : "fly.disabled-other", "{player}", target.getName());
            MessageUtil.sendRaw(target, flying ? "fly.enabled-self" : "fly.disabled-self");
        }
    }
}
