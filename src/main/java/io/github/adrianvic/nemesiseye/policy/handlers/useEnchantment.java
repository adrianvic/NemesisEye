package io.github.adrianvic.nemesiseye.policy.handlers;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class useEnchantment implements NodeHandler {
    @Override
    public boolean allows(HumanEntity entity, PolicyNode node, Action action) {
        ItemStack item = entity.getInventory().getItemInMainHand();
        if (item.getItemMeta() == null) {
            return !node.isWhitelist();
        }

        Map<Enchantment, Integer> enchants = item.getItemMeta().getEnchants();

        if (enchants.isEmpty()) {
            return !node.isWhitelist();
        }

        Map<String, String> valuesmap = DataShifter.parseValueToStringMap(node.values());

        for (Map.Entry<Enchantment, Integer> e : enchants.entrySet()) {
            String enchantment = e.getKey().getKey().getKey();
            String level = e.getValue().toString();

            for (Map.Entry<String, String> entry : valuesmap.entrySet()) {
                if (DataShifter.safeMatches(entry.getKey().trim(), enchantment) && DataShifter.safeMatches(entry.getValue().trim(), level)) {
                    return false;
                }
            }
        }
        return true;
    }
}
