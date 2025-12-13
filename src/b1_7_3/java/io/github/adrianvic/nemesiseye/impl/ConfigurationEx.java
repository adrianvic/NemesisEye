package io.github.adrianvic.nemesiseye.impl;

import io.github.adrianvic.nemesiseye.Nemesis;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigurationEx extends Configuration {
    private final File configFile;
    private final Log logger;
    JavaPlugin plugin;

    public ConfigurationEx(String fileName, Log _logger) {
        super(new File(Nemesis.getInstance().getDataFolder(), fileName));
        this.plugin = Nemesis.getInstance();
        logger = _logger;
        this.configFile = new File(plugin.getDataFolder(), fileName);
    }

    @Override
    public void load() {
        createParentDirectories();

        if (!configFile.exists()) {
            copyDefaultConfig();
        }

        try {
            super.load();
        } catch (Exception e) {
            logger.severe(String.format("Failed to load config '%s': %s", configFile.getName(), e.getMessage()));
        }
    }

    private void createParentDirectories() {
        try {
            Files.createDirectories(configFile.getParentFile().toPath());
        } catch (IOException e) {
            logger.severe(String.format("Failed to generate default config directory: %s", e.getMessage()));
        }
    }

    private void copyDefaultConfig() {
        // Load the config from the JAR directly (it is located at the root level)
        String resourcePath = "/" + configFile.getName(); // Root path of JAR

        try (InputStream input = plugin.getClass().getResourceAsStream(resourcePath)) {
            if (input == null) {
                logger.severe(String.format("Default config '%s' wasn't found in the JAR.", configFile.getName()));
                return;
            }

            Files.copy(input, configFile.toPath());
            if (Files.exists(configFile.toPath())) {
                logger.info(String.format("Default config '%s' generated successfully.", configFile.getName()));
            } else {
                logger.warning("We tried to generate the default config file, but it was not found even after the creation. Maybe your permissions are broken?");
            }
        } catch (IOException e) {
            logger.severe(String.format("Failed to generate default config '%s': %s", configFile.getName(), e.getMessage()));
        }
    }

    public void loadConfig() {
        try {
            this.load();
            logger.info(String.format("Config '%s' loaded successfully.", configFile.getName()));
        } catch (Exception e) {
            logger.severe(String.format("Failed to load config '%s': %s", configFile.getName(), e.getMessage()));
        }
    }

    public void saveConfig() {
        try {
            this.save();
            logger.info(String.format("Config '%s' saved successfully.", configFile.getName()));
        } catch (Exception e) {
            logger.severe(String.format("Failed to save config '%s': %s", configFile.getName(), e.getMessage()));
        }
    }

    public File getConfig() {
        return configFile;
    }
}