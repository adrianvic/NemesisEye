package io.github.adrianvic.nemesiseye.policy;

import org.bukkit.entity.HumanEntity;

public interface NodeHandler {
    boolean check(HumanEntity entity, PolicyNode node, Action action);

    default boolean allows(HumanEntity entity, PolicyNode node, Action action) {
        boolean isWhitelist = (node.effect() == Effect.ALLOWONLY);
        return check(entity, node, action) ? !isWhitelist : isWhitelist;
    }

}