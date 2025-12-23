package io.github.adrianvic.nemesiseye.policy.policies;

import io.github.adrianvic.nemesiseye.policy.Effect;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public record PermissionPolicy(String name, List<String> permissions, List<PolicyNode> nodes, boolean nodeAllowlist, boolean policyAllowList, Effect effect, int weight) implements Policy {

    @Override
    public boolean applies(HumanEntity entity) {
        return true;
    }
}
