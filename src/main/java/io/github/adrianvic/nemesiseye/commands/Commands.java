package io.github.adrianvic.nemesiseye.commands;

import io.github.adrianvic.nemesiseye.commands.sub.*;

import java.util.HashMap;
import java.util.Map;

public class Commands {
    private static final Map<String, Subcommand> commands = new HashMap<>();

    static {
        commands.put("help", new Help());
        commands.put("listpolicies", new ListPolicies());
        commands.put("currentpolicies", new CurrentPolicies());
        commands.put("policyinfo", new PolicyInfo());
        commands.put("reload", new Reload());
    }

    public static Map<String, Subcommand> getAll() {
        return commands;
    }

    public static Subcommand get(String type) {
        return commands.get(type);
    }
}
