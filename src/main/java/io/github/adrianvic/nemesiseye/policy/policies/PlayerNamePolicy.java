package io.github.adrianvic.nemesiseye.policy.policies;

import io.github.adrianvic.nemesiseye.policy.Effect;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;

import java.util.List;

public record PlayerNamePolicy(String name, List<String> playerName, List<PolicyNode> nodes, Effect effect, boolean policyAllowList, int weight) implements Policy {

    @Override
    public boolean applies(HumanEntity entity) {
        if (playerName.contains(entity.getName())) {
            return !policyAllowList();
        } else {
            return policyAllowList();
        }
    }
}