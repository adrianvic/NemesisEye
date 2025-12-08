package io.github.adrianvic.nemesiseye.policy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record PolicyNode(String type, List<Object> values, boolean isWhitelist) {
    public static List<PolicyNode> parseNodes(List<Map<String,Object>> raw, boolean isWhitelist) {
        List<PolicyNode> nodes = new ArrayList<>();

        for (Map<String, Object> m : raw) {
            for (Map.Entry<String, Object> entry : m.entrySet()) {
                String type = entry.getKey();
                List<Object> values = new ArrayList<>();
                Object val = entry.getValue();

                if (val instanceof String s) {
                    values.add(s);
                } else if (val instanceof List<?> l) {
                    values.addAll(l);
                } else if (val instanceof Map<?,?> map) {
                    values.add(map);
                }

                nodes.add(new PolicyNode(type, values, isWhitelist));
            }
        }
        return nodes;
    }

    public NodeHandler getHandler() {
        return NodeHandlers.get(type);
    }
}