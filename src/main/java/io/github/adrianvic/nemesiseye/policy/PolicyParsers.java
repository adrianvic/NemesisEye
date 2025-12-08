package io.github.adrianvic.nemesiseye.policy;

import io.github.adrianvic.nemesiseye.policy.parser.LocationPolicyParser;

import java.util.HashMap;
import java.util.Map;

public class PolicyParsers {
    private static final Map<String, PolicyParser> handlers = new HashMap<>();

    static {
        handlers.put("Location", new LocationPolicyParser());
    }

    public static PolicyParser get(String type) {
        return handlers.get(type);
    }
}
