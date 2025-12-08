package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.policy.LocationPolicy;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class Config {
    private final static Config instance = new Config();
    private File file;
    private YamlConfiguration config;

    private List<LocationPolicy> locationPolicies;
//    private List<PermissionPolicy> permissionPolicies;
//    private List<PlayerNamePolicy> playerNamePolicies;

    private Config() {
    }

    public void load() {
        file = new File(Nemesis.getInstance().getDataFolder(), "settings.yml");

        if (!file.exists())
            Nemesis.getInstance().saveResource("settings.yml", false);

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        locationPolicies = LocationPolicy.parseLocationPolicy(config.getMapList("Policies.Location"));
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public List<LocationPolicy> getLocationPolicies() {
        return locationPolicies;
    }

    public static Config getInstance() {
        return instance;
    }
}