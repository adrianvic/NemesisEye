package io.github.adrianvic.nemesiseye.policy;

import java.util.*;

public class NodeValueParser {
    public static List<String> parseValueToStringList(List<Object> values) {
        List<String> result = new ArrayList<>();
        for (Object o : values) {
            if (o instanceof String) result.add((String) o);
        }
        return result;
    }

    public static Map<String, String> parseValueToStringMap(List<Object> values) {
        Map<String, String> result = new HashMap<>();

        for (Object o : values) {
            if (o instanceof Map<?, ?> raw) {
                for (Map.Entry<?, ?> e : raw.entrySet()) {
                    if (e.getKey() instanceof String k && e.getValue() instanceof String v) {
                        result.put(k, v);
                    }
                }
            }
        }
        return result;
    }
}
