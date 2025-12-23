package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

public class useEnchantment implements NodeHandler {
    private final Glimmer glim = Nemesis.getInstance().getGlimmer();

    @Override
    public boolean check(HumanEntity entity, PolicyNode node, Action action) {
        ItemStack item = glim.getItemInMainHandHumanEntity(entity);

        if (!glim.hasItemMeta(item)) return true;
        if (!glim.hasAnyEnchantment(item)) return true;

        boolean matches = glim.hasEnchantment(item,
                DataShifter.parseValueToStringMap(node.values()));

        return matches;
    }
}
