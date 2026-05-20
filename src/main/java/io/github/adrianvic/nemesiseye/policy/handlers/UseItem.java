package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import java.util.List;

public class UseItem implements NodeHandler {

    private final Glimmer glim = Nemesis.getInstance().getGlimmer();

    @Override
    public boolean check(LivingEntity entity, PolicyNode node, Action action, Event event) {
        if (entity instanceof HumanEntity e) {
            String type = glim.getItemInMainHandHumanEntity(e).getType().toString();
            List<String> parsedValue = DataShifter.parseValueToStringList(node.values());

            return DataShifter.safeMatches(parsedValue, type);
        }

        return false;
    }
}
