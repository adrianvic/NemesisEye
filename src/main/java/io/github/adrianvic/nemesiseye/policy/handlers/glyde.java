package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

public class glyde implements NodeHandler {
    @Override
    public boolean check(HumanEntity entity, PolicyNode node, Action action, Event event) {
        return true;
    }
}
