package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Equip implements NodeHandler {
    private final Glimmer glim = Nemesis.getInstance().getGlimmer();

    @Override
    public boolean check(LivingEntity entity, PolicyNode node, Action action, Event event) {
        ItemStack item = glim.getEquippedItem(event);

        if (!glim.isArmor(item)) {
            return false;
        }

        String type = item.getType().name();
        List<String> parsedValue = DataShifter.parseValueToStringList(node.values());

        return DataShifter.safeMatches(parsedValue, type);
    }
}