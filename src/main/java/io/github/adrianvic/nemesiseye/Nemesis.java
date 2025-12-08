package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.commands.Eye;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nemesis extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        Config.getInstance().load();
        getCommand("eye").setExecutor(new Eye());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Nemesis getInstance() {
        return getPlugin(Nemesis.class);
    }
}