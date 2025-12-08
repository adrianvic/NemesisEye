package io.github.adrianvic.nemesiseye.policy;

import org.bukkit.entity.HumanEntity;

public interface NodeHandler {
    boolean allows(HumanEntity entity, PolicyNode node, Action action);
}