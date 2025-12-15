package io.github.adrianvic.nemesiseye.policy.parser;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.*;
import io.github.adrianvic.nemesiseye.policy.policies.LocationPolicy;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationPolicyParser implements PolicyParser {
    private Glimmer glim = Nemesis.getInstance().getGlimmer();

    public Policy parse(Map<?, ?> raw) {
            String name = (String) raw.get("name");
            boolean allowlist = Boolean.TRUE.equals(raw.get("allowList"));

            // Nodes
            Object rawNodes = raw.get("nodes");
            List<Map<String, Object>> nodeList = new ArrayList<>();
            if (rawNodes instanceof List<?> list) {
                for (Object o : list) {
                    if (o instanceof Map<?, ?> map)
                        nodeList.add((Map<String, Object>) map);
                }
            }

            List<PolicyNode> nodes = PolicyNode.parseNodes(nodeList, allowlist);

            List<Glimmer.Box> locations = DataShifter.configLocationParser(raw.get("locations"));

        return new LocationPolicy(name, locations, nodes, allowlist);
    }
}
