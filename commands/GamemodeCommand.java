package me.ckanto.core.commands;

import me.ckanto.core.CkantoCore;
import me.ckanto.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeCommand implements CommandExecutor {

    private final CkantoCore plugin;
    private final GameMode gameMode;
    private final String modeName;
    private final String permission;

    public GamemodeCommand(CkantoCore plugin, String mode) {
        this.plugin = plugin;
        this.gameMode = GameMode.valueOf(mode);
        this.modeName = mode.substring(0, 1).toUpperCase() + mode.substring(1).toLowerCase();
        this.permission = "ckanto." + mode.toLowerCase().replace("_", "").substring(0, 3);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission(permission)) {
            MessageUtil.send(sender, "no-permission");
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                MessageUtil.send(sender, "player-only");
                return true;
            }
            player.setGameMode(gameMode);
            MessageUtil.sendRaw(sender, "gamemode.set-self", "{mode}", modeName);
        } else {
            if (!sender.hasPermission(permission + ".others")) {
                MessageUtil.send(sender, "no-permission");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                MessageUtil.sendRaw(sender, "messages.player-not-found", "{player}", args[0]);
                return true;
            }
            target.setGameMode(gameMode);
            MessageUtil.sendRaw(sender, "gamemode.set-other", "{player}", target.getName(), "{mode}", modeName);
            MessageUtil.sendRaw(target, "gamemode.set-self", "{mode}", modeName);
        }
        return true;
    }
}
