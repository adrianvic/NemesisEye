package io.github.adrianvic.nemesiseye.impl;

import static org.bukkit.Bukkit.getServer;

import io.github.adrianvic.nemesiseye.Nemesis;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Log {
    JavaPlugin plugin;
    PluginDescriptionFile pdf;

    public Log() {
        plugin = Nemesis.getInstance();
        pdf = plugin.getDescription();
    }

    public void info(String message) {
        getServer().getLogger().info("[" + pdf.getName() + "] " +  message);
    }

    public void infoc(String message) {
        getServer().getLogger().info("[" + pdf.getName() + "] " +  message);
    }

    public void warning(String message) {
        getServer().getLogger().warning("[" + pdf.getName() + "] " +  message);
    }

    public void warningc(String message) {
        getServer().getLogger().warning("[" + pdf.getName() + "] " +  message);
    }

    public void severe(String message) {
        getServer().getLogger().severe("[" + pdf.getName() + "] " +  message);
    }

    public void severec(String message) {
        getServer().getLogger().severe("[" + pdf.getName() + "] " +  message);
    }
}