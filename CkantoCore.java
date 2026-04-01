package me.ckanto.core;

import me.ckanto.core.commands.*;
import me.ckanto.core.managers.KitManager;
import me.ckanto.core.managers.WarpManager;
import me.ckanto.core.utils.MessageUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class CkantoCore extends JavaPlugin {

    private static CkantoCore instance;
    private WarpManager warpManager;
    private KitManager kitManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        MessageUtil.init(this);

        warpManager = new WarpManager(this);
        kitManager = new KitManager(this);

        registerCommands();

        getLogger().info("CkantoCore abilitato - by ckanto");
    }

    @Override
    public void onDisable() {
        if (warpManager != null) warpManager.save();
        if (kitManager != null) kitManager.save();
        getLogger().info("CkantoCore disabilitato.");
    }

    private void registerCommands() {
        getCommand("fly").setExecutor(new FlyCommand(this));
        getCommand("flyspeed").setExecutor(new FlySpeedCommand(this));
        getCommand("gmc").setExecutor(new GamemodeCommand(this, "CREATIVE"));
        getCommand("gms").setExecutor(new GamemodeCommand(this, "SURVIVAL"));
        getCommand("gmsp").setExecutor(new GamemodeCommand(this, "SPECTATOR"));
        getCommand("tpto").setExecutor(new TpToCommand(this));
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("kit").setExecutor(new KitCommand(this));
    }

    public static CkantoCore getInstance() {
        return instance;
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }
}
