package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.commands.Eye;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Nemesis extends JavaPlugin {
    private Glimmer glim;
    private static final String VERSION_PROP = "impl.version";
    private static Nemesis instance;

    @Override
    public void onEnable() {
        instance = this;
        glim = loadGlim();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        Config.getInstance().load();
        getCommand("eye").setExecutor(new Eye());
    }

    private String readImplVersion() {
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("version.properties")) {
            if (is == null) {
                throw new IllegalStateException("version.properties not found on classpath.");
            }
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load version.properties", e);
        }
        String version = props.getProperty(VERSION_PROP);
        if (version == null || version.isBlank()) {
            throw new IllegalStateException(VERSION_PROP + " property missing in version.properties.");
        }
        return version.trim();
    }

    private Glimmer loadGlim() {
        String implVersion = readImplVersion();
        String className = "io.github.adrianvic.nemesiseye.impl." + implVersion;

        try {
            Class<?> clazz = Class.forName(className, true, getClass().getClassLoader());
            if (!Glimmer.class.isAssignableFrom(clazz)) {
                throw new IllegalStateException(className + " does not implement Glimmer.");
            }
            return (Glimmer) clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to instantiate " + className, e);
        }
    }

    @Override
    public void onDisable() {
    }

    public static Nemesis getInstance() {
        return instance;
    }

    public Glimmer getGlimmer() { return glim; }
}