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

    public static boolean safeMatches(List<String> expressions, String against) {
        for (String s : expressions) {
            if (DataShifter.safeMatches(s, against)) {
                return true;
            }
        }

        return false;
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

    public static <T extends Enum<T>> T enumOrDefault(Class<T> type, String string, T def) {
        try {
            return Enum.valueOf(type, string);
        } catch (IllegalArgumentException e) {
            return def;
        } catch (Exception e) {
            e.printStackTrace();
            return def;
        }
    }
}
