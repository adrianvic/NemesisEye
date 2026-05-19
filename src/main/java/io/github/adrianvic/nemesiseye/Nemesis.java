package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import io.github.adrianvic.nemesiseye.reflection.VersionMatcher;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nemesis extends JavaPlugin {
    private Glimmer glim;
    private static final String VERSION_PROP = "impl.version";
    private static Nemesis instance;

    @Override
    public void onEnable() {
        instance = this;
        glim = new VersionMatcher().loadGlim();
        glim.onLoad();
        Config.getInstance().load();
    }

    @Override
    public void onDisable() {
    }

    public static Nemesis getInstance() { return instance; }
    public Glimmer getGlimmer() { return glim; }
    public PluginManager getPluginManager() { return this.getServer().getPluginManager(); }
}
