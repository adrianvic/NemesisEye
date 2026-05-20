package io.github.adrianvic.nemesiseye.policy;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

public interface NodeHandler {
    boolean check(LivingEntity entity, PolicyNode node, Action action, Event event);
}