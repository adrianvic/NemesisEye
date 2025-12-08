package io.github.adrianvic.nemesiseye.policy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record LocationPolicy(String name, List<ArrayList<BoundingBox>> locations, List<PolicyNode> nodes, boolean allowlist) {
    public static List<LocationPolicy> parseLocationPolicy(List<Map<?,?>> raw) {
        List<LocationPolicy> out = new ArrayList<>(raw.size());
        for (Map<?,?> m : raw) {
            String name = (String) m.get("name");
            boolean allowlist = Boolean.TRUE.equals(m.get("allowList"));

            // Nodes
            Object rawNodes = m.get("nodes");
            List<Map<String, Object>> nodeList = new ArrayList<>();
            if (rawNodes instanceof  List<?> list) {
                for (Object o : list) {
                    if (o instanceof Map<?, ?> map)
                        nodeList.add((Map<String, Object>) map);
                }
            }

            List<PolicyNode> nodes = PolicyNode.parseNodes(nodeList, allowlist);

            // Parsing locations
            List<ArrayList<BoundingBox>> locations = new ArrayList<>();
            Object rawGroups = m.get("locations");
            List<?> groups = rawGroups instanceof List ? (List<?>) rawGroups : List.of();

            // Getting groups
            for (Object gObj : groups) {
                List<?> group = (List<?>) gObj;
                ArrayList<BoundingBox> boxes = new ArrayList<>(group.size());

                // Now iterate over regions inside the group
                for (Object rObj : group) {
                    Map<?,?> region = (Map<?, ?>) rObj;
                    Map<?,?> c1 = (Map<?, ?>) region.get("corner1");
                    Map<?,?> c2 = (Map<?, ?>) region.get("corner2");

                    double x1 = ((Number) c1.get("x")).doubleValue();
                    double y1 = ((Number) c1.get("y")).doubleValue();
                    double z1 = ((Number) c1.get("z")).doubleValue();

                    double x2 = ((Number) c2.get("x")).doubleValue();
                    double y2 = ((Number) c2.get("y")).doubleValue();
                    double z2 = ((Number) c2.get("z")).doubleValue();

                    Location loc1 = new Location(Bukkit.getWorlds().getFirst(), x1, y1, z1);
                    Location loc2 = new Location(Bukkit.getWorlds().getFirst(), x2, y2, z2);

                    boxes.add(BoundingBox.of(loc1, loc2));
                }
                locations.add(boxes);
            }
            out.add(new LocationPolicy(name, locations, nodes, allowlist));
        }
        return out;
    }
}