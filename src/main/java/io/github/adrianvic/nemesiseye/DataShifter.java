package io.github.adrianvic.nemesiseye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DataShifter {
    public static boolean safeMatches(String expression, String against) {
        String cleanPattern = expression.trim();
        Pattern pattern = Pattern.compile(cleanPattern, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(against).matches();
    }

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

    public static List<Map<?, ?>> parseValueToListOfMaps(List<?> values) {
        List<Map<?, ?>> result = new ArrayList<>();

        for (Object o : values) {
            if (o instanceof Map<?, ?> raw) {
                result.add(raw);
            }
        }
        return result;
    }
}
