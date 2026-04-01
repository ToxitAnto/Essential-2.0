package me.ckanto.core.commands;

import me.ckanto.core.CkantoCore;
import me.ckanto.core.managers.KitManager;
import me.ckanto.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class KitCommand implements CommandExecutor {

    private final CkantoCore plugin;
    private final KitManager kitManager;

    public KitCommand(CkantoCore plugin) {
        this.plugin = plugin;
        this.kitManager = plugin.getKitManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("ckanto.kit")) {
            MessageUtil.send(sender, "no-permission");
            return true;
        }

        if (args.length == 0) {
            sendList(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "list" -> sendList(sender);
            case "create" -> handleCreate(sender, args);
            case "delete", "del" -> handleDelete(sender, args);
            default -> handleClaim(sender, args[0]);
        }
        return true;
    }

    private void handleClaim(CommandSender sender, String name) {
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "player-only");
            return;
        }
        KitManager.KitData data = kitManager.getKit(name);
        if (data == null) {
            MessageUtil.sendRaw(sender, "kit.not-found", "{kit}", name);
            return;
        }
        if (!player.hasPermission("ckanto.kit.bypass")) {
            long remaining = kitManager.getRemainingCooldown(player, name);
            if (remaining > 0) {
                MessageUtil.sendRaw(sender, "kit.cooldown", "{time}", MessageUtil.formatTime(remaining), "{kit}", name);
                return;
            }
        }
        for (ItemStack item : data.items()) {
            if (item != null) {
                player.getInventory().addItem(item.clone()).forEach((slot, leftover) ->
                    player.getWorld().dropItem(player.getLocation(), leftover)
                );
            }
        }
        kitManager.setCooldown(player, name);
        MessageUtil.sendRaw(sender, "kit.claimed", "{kit}", name);
    }

    private void handleCreate(CommandSender sender, String[] args) {
        if (!sender.hasPermission("ckanto.kit.create")) {
            MessageUtil.send(sender, "no-permission");
            return;
        }
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "player-only");
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(MessageUtil.colorize("&cUso: /kit create <nome> [cooldown-secondi]"));
            return;
        }
        String name = args[1];
        long cooldown = 0;
        if (args.length >= 3) {
            try { cooldown = Long.parseLong(args[2]); } catch (NumberFormatException ignored) {}
        }
        if (kitManager.exists(name)) {
            MessageUtil.sendRaw(sender, "kit.already-exists", "{kit}", name);
            return;
        }
        List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) items.add(item.clone());
        }
        if (items.isEmpty()) {
            MessageUtil.sendRaw(sender, "kit.no-inventory");
            return;
        }
        kitManager.createKit(name, items, cooldown);
        MessageUtil.sendRaw(sender, "kit.created", "{kit}", name);
    }

    private void handleDelete(CommandSender sender, String[] args) {
        if (!sender.hasPermission("ckanto.kit.delete")) {
            MessageUtil.send(sender, "no-permission");
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(MessageUtil.colorize("&cUso: /kit delete <nome>"));
            return;
        }
        String name = args[1];
        if (!kitManager.deleteKit(name)) {
            MessageUtil.sendRaw(sender, "kit.not-found", "{kit}", name);
            return;
        }
        MessageUtil.sendRaw(sender, "kit.deleted", "{kit}", name);
    }

    private void sendList(CommandSender sender) {
        Set<String> kits = kitManager.getKits();
        sender.sendMessage(MessageUtil.colorize(plugin.getConfig().getString("kit.list-header", "&8&m-----&r &bKit &8&m-----")));
        if (kits.isEmpty()) {
            MessageUtil.sendRaw(sender, "kit.list-empty");
            return;
        }
        StringJoiner joiner = new StringJoiner("&8, &a", "&a", "");
        kits.stream().sorted().forEach(joiner::add);
        sender.sendMessage(MessageUtil.colorize(joiner.toString()));
    }
}
