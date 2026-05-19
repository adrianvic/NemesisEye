package io.github.adrianvic.nemesiseye.policy;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

public interface NodeHandler {
    boolean check(HumanEntity entity, PolicyNode node, Action action, Event event);
}