package io.github.adrianvic.nemesiseye.policy.policies;

import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.Effect;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public record PermissionPolicy(String name, List<String> worlds, List<String> permissions, List<PolicyNode> nodes, boolean policyAllowList, Effect effect, int weight) implements Policy {

    @Override
    public boolean applies(LivingEntity entity) {
        for (String perm : permissions) {
            if (Nemesis.getInstance().getGlimmer().hasPermission(entity, perm)) {
                return true;
            }
        }

        return false;
    }
}
