package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.entity.HumanEntity;

public class useItem implements NodeHandler {

    private Glimmer glim = Nemesis.getInstance().getGlimmer();

    @Override
    public boolean allows(HumanEntity entity, PolicyNode node, Action action) {
        String type = glim.getItemInMainHandHumanEntity(entity).getType().toString();

        for (String s : DataShifter.parseValueToStringList(node.values())) {
            boolean matches = DataShifter.safeMatches(s, type);
            if (matches) return node.isWhitelist();
        }
        return !node.isWhitelist();
    }
}
