package me.ckanto.core.commands;

import me.ckanto.core.CkantoCore;
import me.ckanto.core.managers.WarpManager;
import me.ckanto.core.utils.MessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.StringJoiner;

public class WarpCommand implements CommandExecutor {

    private final CkantoCore plugin;
    private final WarpManager warpManager;

    public WarpCommand(CkantoCore plugin) {
        this.plugin = plugin;
        this.warpManager = plugin.getWarpManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("ckanto.warp")) {
            MessageUtil.send(sender, "no-permission");
            return true;
        }

        if (args.length == 0) {
            sendList(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "list" -> sendList(sender);
            case "set" -> handleSet(sender, args);
            case "del", "delete" -> handleDelete(sender, args);
            default -> handleTeleport(sender, args[0]);
        }
        return true;
    }

    private void handleTeleport(CommandSender sender, String name) {
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "player-only");
            return;
        }
        Location loc = warpManager.getWarp(name);
        if (loc == null) {
            MessageUtil.sendRaw(sender, "warp.not-found", "{warp}", name);
            return;
        }
        player.teleport(loc);
        MessageUtil.sendRaw(sender, "warp.teleported", "{warp}", name);
    }

    private void handleSet(CommandSender sender, String[] args) {
        if (!sender.hasPermission("ckanto.warp.set")) {
            MessageUtil.send(sender, "no-permission");
            return;
        }
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "player-only");
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(MessageUtil.colorize("&cUso: /warp set <nome>"));
            return;
        }
        String name = args[1];
        if (warpManager.exists(name)) {
            MessageUtil.sendRaw(sender, "warp.already-exists", "{warp}", name);
            return;
        }
        warpManager.setWarp(name, player.getLocation());
        MessageUtil.sendRaw(sender, "warp.set", "{warp}", name);
    }

    private void handleDelete(CommandSender sender, String[] args) {
        if (!sender.hasPermission("ckanto.warp.del")) {
            MessageUtil.send(sender, "no-permission");
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(MessageUtil.colorize("&cUso: /warp del <nome>"));
            return;
        }
        String name = args[1];
        if (!warpManager.deleteWarp(name)) {
            MessageUtil.sendRaw(sender, "warp.not-found", "{warp}", name);
            return;
        }
        MessageUtil.sendRaw(sender, "warp.deleted", "{warp}", name);
    }

    private void sendList(CommandSender sender) {
        Set<String> warps = warpManager.getWarps();
        sender.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("warp.list-header", "&8&m-----&r &bWarps &8&m-----")));
        if (warps.isEmpty()) {
            MessageUtil.sendRaw(sender, "warp.list-empty");
            return;
        }
        StringJoiner joiner = new StringJoiner("&8, &a", "&a", "");
        warps.stream().sorted().forEach(joiner::add);
        sender.sendMessage(MessageUtil.colorize(joiner.toString()));
    }
}
