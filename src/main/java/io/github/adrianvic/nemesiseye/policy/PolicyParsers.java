package io.github.adrianvic.nemesiseye.policy;

import io.github.adrianvic.nemesiseye.policy.parser.GlobalPolicyParser;
import io.github.adrianvic.nemesiseye.policy.parser.LocationPolicyParser;
import io.github.adrianvic.nemesiseye.policy.parser.PermissionPolicyParser;
import io.github.adrianvic.nemesiseye.policy.parser.PlayerNamePolicyParser;

import java.util.HashMap;
import java.util.Map;

public class PolicyParsers {
    private static final Map<String, PolicyParser> handlers = new HashMap<>();

    static {
        handlers.put("location", new LocationPolicyParser());
        handlers.put("global", new GlobalPolicyParser());
        handlers.put("playerName", new PlayerNamePolicyParser());
        handlers.put("permission", new PermissionPolicyParser());
    }

    public static PolicyParser get(String type) {
        return handlers.get(type);
    }
}
