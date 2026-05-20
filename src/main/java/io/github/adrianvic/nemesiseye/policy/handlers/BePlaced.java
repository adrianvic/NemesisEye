package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

public class BePlaced implements NodeHandler {
    @Override
    public boolean check(LivingEntity entity, PolicyNode node, Action action, Event event) {
        if (event instanceof BlockPlaceEvent bpe) {
            String type = bpe.getBlock().getType().toString();

            for (String s : DataShifter.parseValueToStringList(node.values())) {
                if (DataShifter.safeMatches(s, type)) {
                    return true;
                }
            }
        }
        return false;
    }
}
