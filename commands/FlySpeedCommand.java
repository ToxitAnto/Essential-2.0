package me.ckanto.core.commands;

import me.ckanto.core.CkantoCore;
import me.ckanto.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlySpeedCommand implements CommandExecutor {

    private final CkantoCore plugin;

    public FlySpeedCommand(CkantoCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("ckanto.flyspeed")) {
            MessageUtil.send(sender, "no-permission");
            return true;
        }

        if (args.length == 0) {
            MessageUtil.sendRaw(sender, "flyspeed.invalid");
            return true;
        }

        int speed;
        try {
            speed = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            MessageUtil.sendRaw(sender, "flyspeed.invalid");
            return true;
        }

        if (speed < 1 || speed > 10) {
            MessageUtil.sendRaw(sender, "flyspeed.invalid");
            return true;
        }

        float bukkit = speed / 10.0f;

        if (args.length == 1) {
            if (!(sender instanceof Player player)) {
                MessageUtil.send(sender, "player-only");
                return true;
            }
            player.setFlySpeed(bukkit);
            MessageUtil.sendRaw(sender, "flyspeed.set-self", "{speed}", String.valueOf(speed));
        } else {
            if (!sender.hasPermission("ckanto.flyspeed.others")) {
                MessageUtil.send(sender, "no-permission");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                MessageUtil.sendRaw(sender, "messages.player-not-found", "{player}", args[1]);
                return true;
            }
            target.setFlySpeed(bukkit);
            MessageUtil.sendRaw(sender, "flyspeed.set-other", "{player}", target.getName(), "{speed}", String.valueOf(speed));
        }
        return true;
    }
}
