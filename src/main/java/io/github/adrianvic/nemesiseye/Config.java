package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyParsers;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Config {
    private final static Config instance = new Config();
    private File file;
    private YamlConfiguration config;

    private List<Policy> policies = new ArrayList<>();

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

        List<Map<?, ?>> rawPolicies = config.getMapList("Policies");
        for (Map<?, ?> map : rawPolicies) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getKey() instanceof String k && entry.getValue() instanceof List<?> v) {
                    List<Policy> parsed = PolicyParsers.get(k).parse(v);
                    policies.addAll(parsed);
                }
            }
        }
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

    public List<Policy> getPolicies() {
        return policies;
    }

    public static Config getInstance() {
        return instance;
    }
}