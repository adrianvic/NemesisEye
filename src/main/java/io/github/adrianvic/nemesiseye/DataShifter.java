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

    public static Map<String,String> parseValueToStringMap(List<Object> raw) {
        Map<String,String> out = new HashMap<>();

        for (Object o : raw) {
            if (o instanceof Map<?,?> map) {
                for (Map.Entry<?,?> e : map.entrySet()) {
                    out.put(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
                }
            } else if (o instanceof String s) {

                String[] parts = s.split(":", 2);
                if (parts.length == 2) {
                    out.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
        return out;
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
