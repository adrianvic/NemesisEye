package io.github.adrianvic.nemesiseye.policy;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.policy.policies.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PolicyParser {
    default Policy parse(Map<?, ?> raw) {
        boolean policyAllowList = Boolean.TRUE.equals(raw.get("policyAllowList"));
        boolean nodesAllowList = Boolean.TRUE.equals(raw.get("nodesAllowList"));
        Effect effect = DataShifter.enumOrDefault(Effect.class, (String) raw.get("effect"), Effect.DENY);
        String name = (String) raw.get("name");
        Integer weightObj = (Integer) raw.get("weight");
        int weight = weightObj != null ? weightObj : 0;

        // Worlds
        List<String> worlds = new ArrayList<>();
        if (raw.get("worlds") instanceof List<?> list) {
            for (Object object : list) {
                if (object instanceof String result) {
                    worlds.add(result);
                }
            }
        } else {
            worlds.add("world");
        }

        // Nodes
        Object rawNodes = raw.get("nodes");
        List<Map<Object, Object>> nodeList = new ArrayList<>();

        if (rawNodes instanceof List<?> list) {
            for (Object o : list) {
                if (o instanceof Map<?, ?> map)
                    nodeList.add((Map<Object, Object>) map);
            }
        }

        List<PolicyNode> nodes = PolicyNode.parseNodes(nodeList, effect);
        return parse(new Core(name, worlds, nodes, nodesAllowList, policyAllowList, effect, weight), raw);
    }

    Policy parse(Core corePolicy, Map<?, ?> raw);
}
