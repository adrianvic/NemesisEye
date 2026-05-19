package io.github.adrianvic.nemesiseye.policy.parser;

import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.*;
import io.github.adrianvic.nemesiseye.policy.policies.Core;
import io.github.adrianvic.nemesiseye.policy.policies.LocationPolicy;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationPolicyParser implements PolicyParser {
    private Glimmer glim = Nemesis.getInstance().getGlimmer();

    public Policy parse(Core corePolicy, Map<?, ?> raw) {
        Object rawLocations = raw.get("locations");
        Object rawCoordinates = null;
        List<String> worlds = new ArrayList<>();
        if (rawLocations instanceof Map<?,?> rawLocationMap) {
            rawCoordinates = rawLocationMap.get("coordinates");

            if (rawLocationMap.get("worlds") instanceof List<?> rawWorldsList) {
                for (Object worldObject : rawWorldsList) {
                    if (worldObject instanceof String worldString) {
                        worlds.add(worldString);
                    }
                }
            } else {
                worlds.add("world");
            }
        }

        List<Glimmer.Box> locations = new ArrayList<>();

        // Parsing locations
        List<?> groups = rawCoordinates instanceof List ? (List<?>) rawCoordinates : List.of();

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

            locations.add(Glimmer.Box.of(loc1, loc2));
        }

        return new LocationPolicy(corePolicy.name(), corePolicy.worlds(), locations, corePolicy.nodes(), corePolicy.nodeAllowlist(), corePolicy.policyAllowList(), corePolicy.effect(), corePolicy.weight());
    }
}
