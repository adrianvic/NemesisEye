package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

public class Glyde implements NodeHandler {
    @Override
    public boolean check(LivingEntity entity, PolicyNode node, Action action, Event event) {
        return true;
    }
}
