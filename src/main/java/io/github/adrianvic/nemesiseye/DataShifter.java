package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.Location;

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

    public static List<Glimmer.Box> configLocationParser(Object rawLocations) {
        Glimmer glim = Nemesis.getInstance().getGlimmer();

        if (rawLocations == null) {
            return List.of();
        }

        // Parsing locations
        List<?> groups = rawLocations instanceof List ? (List<?>) rawLocations : List.of();

        ArrayList<Glimmer.Box> boxes = new ArrayList<>(groups.size());

        // Now iterate over regions
        for (Object rObj : groups) {
            Map<?, ?> region = (Map<?, ?>) rObj;
            Map<?, ?> c1 = (Map<?, ?>) region.get("corner1");
            Map<?, ?> c2 = (Map<?, ?>) region.get("corner2");

            double x1 = ((Number) c1.get("x")).doubleValue();
            double y1 = ((Number) c1.get("y")).doubleValue();
            double z1 = ((Number) c1.get("z")).doubleValue();

            double x2 = ((Number) c2.get("x")).doubleValue();
            double y2 = ((Number) c2.get("y")).doubleValue();
            double z2 = ((Number) c2.get("z")).doubleValue();

            Location loc1 = new Location(glim.getWorlds().getFirst(), x1, y1, z1);
            Location loc2 = new Location(glim.getWorlds().getFirst(), x2, y2, z2);

            boxes.add(Glimmer.Box.of(loc1, loc2));
        }

        return boxes;
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
