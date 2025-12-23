package io.github.adrianvic.nemesiseye.policy;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

import java.util.List;

public interface Policy {
    String name();
    List<PolicyNode> nodes();

    boolean policyAllowList();
    boolean applies(HumanEntity entity);
    Effect effect();
    int weight();
    default void addNode(PolicyNode node) {
        nodes().add(node);
    }
    default boolean matches(HumanEntity entity, Action action, Event event) {
        for (PolicyNode node : nodes()) {
            if (node.matches(entity, action, event)) {
                return true;
            }
        }
        return false;
    }
}
