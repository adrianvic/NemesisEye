package io.github.adrianvic.nemesiseye.policy.policies;

import io.github.adrianvic.nemesiseye.policy.Effect;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;

import java.util.List;

public record GlobalPolicy(String name, List<PolicyNode> nodes, boolean policyAllowList, Effect effect, int weight) implements Policy {
    public boolean applies(HumanEntity entity) {
        return true;
    }
}
