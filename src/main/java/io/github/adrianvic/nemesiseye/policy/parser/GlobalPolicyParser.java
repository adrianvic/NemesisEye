package io.github.adrianvic.nemesiseye.policy.parser;

import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.policy.PolicyParser;
import io.github.adrianvic.nemesiseye.policy.policies.GlobalPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlobalPolicyParser implements PolicyParser {
    @Override
    public Policy parse(Map<?, ?> raw) {
        boolean allowlist = (boolean) raw.get("allowList");
        String name = (String) raw.get("name");

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

        return new GlobalPolicy(name, nodes, allowlist);
    }
}
