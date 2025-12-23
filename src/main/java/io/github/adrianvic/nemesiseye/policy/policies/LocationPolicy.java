package io.github.adrianvic.nemesiseye.policy.policies;

import io.github.adrianvic.nemesiseye.policy.Effect;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.entity.HumanEntity;

import java.util.List;

public record LocationPolicy(String name, List<Glimmer.Box> locations, List<PolicyNode> nodes, boolean nodeAllowlist, boolean policyAllowList, Effect effect, int weight) implements Policy {
    @Override
    public boolean applies(HumanEntity entity) {
        for (Glimmer.Box box : locations) {
            if (box.contains(entity.getLocation().toVector())) return !policyAllowList;
        }
        return policyAllowList;
    }
}