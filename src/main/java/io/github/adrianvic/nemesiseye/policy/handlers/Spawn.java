package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import java.util.List;

public class Spawn implements NodeHandler {

    @Override
    public boolean check(LivingEntity entity, PolicyNode node, Action action, Event event) {
        String type = entity.getType().name();
        List<String> parsedValue = DataShifter.parseValueToStringList(node.values());

        return DataShifter.safeMatches(parsedValue, type);
    }
}
