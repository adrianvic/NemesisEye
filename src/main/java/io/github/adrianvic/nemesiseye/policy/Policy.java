package io.github.adrianvic.nemesiseye.policy;

import org.bukkit.entity.HumanEntity;

import java.util.List;

public interface Policy {
    String name();
    List<PolicyNode> nodes();
    boolean nodeAllowlist();
    boolean policyAllowList();
    boolean applies(HumanEntity entity);
    Effect effect();
    int weight();
    default void addNode(PolicyNode node) {
        nodes().add(node);
    }
}
