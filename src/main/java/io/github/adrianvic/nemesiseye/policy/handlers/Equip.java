package io.github.adrianvic.nemesiseye.policy.handlers;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Equip implements NodeHandler {
    private final Glimmer glim = Nemesis.getInstance().getGlimmer();

    @Override
    public boolean check(LivingEntity entity, PolicyNode node, Action action, Event event) {
        ItemStack item = null;

        if (event instanceof PlayerArmorChangeEvent e) {
            item = e.getNewItem();
        }

        else if (event instanceof InventoryClickEvent e) {
            InventoryType.SlotType slotType = e.getSlotType();

            if (e.getClick() == ClickType.NUMBER_KEY // hotbar key swap
                    && slotType == InventoryType.SlotType.ARMOR
                    && entity instanceof Player player) {
                item = player.getInventory().getItem(e.getHotbarButton());
            }

            else if (e.isShiftClick()) {
                ItemStack current = e.getCurrentItem();

                if (glim.isArmor(current)) {
                    item = current;
                }
            }

            // regular click
            else if (slotType == InventoryType.SlotType.ARMOR) {
                ItemStack cursor = e.getCursor();

                if (glim.isArmor(cursor)) {
                    item = cursor;
                }
            }
        }

        if (!glim.isArmor(item)) {
            return false;
        }

        String type = item.getType().name();
        List<String> parsedValue = DataShifter.parseValueToStringList(node.values());

        return DataShifter.safeMatches(parsedValue, type);
    }
}