package io.github.adrianvic.nemesiseye.policy.policies;

import io.github.adrianvic.nemesiseye.policy.Effect;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;

import java.util.List;

public record PermissionPolicy(String name, List<String> permissions, List<PolicyNode> nodes, boolean policyAllowList, Effect effect, int weight) implements Policy {

    @Override
    public boolean applies(HumanEntity entity) {
        for (String perm : permissions) {
            if (entity.hasPermission(perm)) {
                return true;
            }
        }

        return false;
    }
}
