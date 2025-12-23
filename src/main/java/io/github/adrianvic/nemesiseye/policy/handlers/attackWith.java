package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.entity.HumanEntity;

public class attackWith implements NodeHandler {

    private final static Glimmer glim = Nemesis.getInstance().getGlimmer();

    @Override
    public boolean check(HumanEntity entity, PolicyNode node, Action action) {
        if (action == Action.HIT) {
            for (String s : DataShifter.parseValueToStringList(node.values())) {
                boolean matches = DataShifter.safeMatches(s, glim.getItemInMainHandHumanEntity(entity).getType().toString());
                if (matches) return false;
            }
        }
        return true;
    }
}
