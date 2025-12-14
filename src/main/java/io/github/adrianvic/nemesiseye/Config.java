package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private static Config instance = new Config();
    private final Glimmer glim = Nemesis.getInstance().getGlimmer();
    private File file;

    private List<Policy> policies = new ArrayList<>();

    private Config() {}

    public void load() {
        policies = glim.loadPoliciesFromFile(glim.loadConfigFile());
    }
// TODO: Implement config saving
//
//    public void save() {
//        try {
//            config.save(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void set(String path, Object value) {
//        config.set(path, value);
//        save();
//    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public static Config getInstance() {
        return instance;
    }
}