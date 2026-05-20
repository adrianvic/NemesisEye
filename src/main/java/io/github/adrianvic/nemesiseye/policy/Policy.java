package io.github.adrianvic.nemesiseye.policy;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import java.util.List;

public interface Policy {
    String name();
    List<PolicyNode> nodes();
    boolean policyAllowList();
    boolean applies(LivingEntity entity);
    Effect effect();
    int weight();
    List<String> worlds();

    default void addNode(PolicyNode node) {
        nodes().add(node);
    }

    default boolean matches(LivingEntity entity, Action action, Event event) {
        if (!worlds().contains(entity.getWorld().getName())) {
            return false;
        }

        for (PolicyNode node : nodes()) {
            if (node.matches(entity, action, event)) {
                return true;
            }
        }

        return false;
    }
}
