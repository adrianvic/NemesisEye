package io.github.adrianvic.nemesiseye.policy.handlers;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class Equip implements NodeHandler {

    @Override
    public boolean check(LivingEntity entity, PolicyNode node, Action action, Event event) {
        ItemStack item = null;

        if (event instanceof PlayerArmorChangeEvent e) {
            // Right click equip, dispenser equip, etc...
            item = e.getNewItem();
        }

        else if (event instanceof InventoryClickEvent e) {
            InventoryType.SlotType slotType = e.getSlotType();

            // Number key swap into armor slot
            if (e.getClick() == ClickType.NUMBER_KEY
                    && slotType == InventoryType.SlotType.ARMOR
                    && entity instanceof Player player) {
                item = player.getInventory().getItem(e.getHotbarButton());
            }

            // Shift click armor from inventory
            else if (e.isShiftClick()) {
                ItemStack current = e.getCurrentItem();

                if (isArmor(current)) {
                    item = current;
                }
            }

            // Cursor click onto armor slot
            else if (slotType == InventoryType.SlotType.ARMOR) {
                ItemStack cursor = e.getCursor();

                if (isArmor(cursor)) {
                    item = cursor;
                }
            }
        }

        if (!isArmor(item)) {
            return false;
        }

        String type = item.getType().name();

        for (String s : DataShifter.parseValueToStringList(node.values())) {
            if (DataShifter.safeMatches(s, type)) {
                return true;
            }
        }

        return false;
    }

    private boolean isArmor(ItemStack item) {
        if (item == null || item.getType().isAir()) {
            return false;
        }

        String name = item.getType().name();

        return name.endsWith("_HELMET")
                || name.endsWith("_CHESTPLATE")
                || name.endsWith("_LEGGINGS")
                || name.endsWith("_BOOTS")
                || item.getType() == org.bukkit.Material.ELYTRA;
    }
}