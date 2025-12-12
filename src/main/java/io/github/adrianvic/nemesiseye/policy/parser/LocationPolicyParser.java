package io.github.adrianvic.nemesiseye.policy.parser;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.*;
import io.github.adrianvic.nemesiseye.policy.policies.LocationPolicy;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationPolicyParser implements PolicyParser {
    private Glimmer glim = Nemesis.getInstance().getGlimmer();

    public List<Policy> parse(List<?> raw) {
        List<Policy> out = new ArrayList<>(raw.size());
        List<Map<?, ?>> parsedRawMap = DataShifter.parseValueToListOfMaps(raw);

        for (Map<?, ?> m : parsedRawMap) {
            String name = (String) m.get("name");
            boolean allowlist = Boolean.TRUE.equals(m.get("allowList"));

            // Nodes
            Object rawNodes = m.get("nodes");
            List<Map<String, Object>> nodeList = new ArrayList<>();
            if (rawNodes instanceof List<?> list) {
                for (Object o : list) {
                    if (o instanceof Map<?, ?> map)
                        nodeList.add((Map<String, Object>) map);
                }
            }

            List<PolicyNode> nodes = PolicyNode.parseNodes(nodeList, allowlist);

            // Parsing locations
            List<ArrayList<Glimmer.Box>> locations = new ArrayList<>();

            Object rawLocations = m.get("locations");
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
            locations.add(boxes);

            out.add(new LocationPolicy(name, locations, nodes, allowlist));
        }
        return out;
    }
}
