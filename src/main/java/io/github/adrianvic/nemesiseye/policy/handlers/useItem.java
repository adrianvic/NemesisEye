package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;

public class useItem implements NodeHandler {

    @Override
    public boolean allows(HumanEntity entity, PolicyNode node, Action action) {
        String type = entity.getInventory().getItemInMainHand().getType().toString();

        for (String s : DataShifter.parseValueToStringList(node.values())) {
            if (DataShifter.safeMatches(s, type)) {
                return false;
            }
        }
        return true;
    }
}
