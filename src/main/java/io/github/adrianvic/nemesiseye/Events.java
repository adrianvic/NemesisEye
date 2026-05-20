package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.policy.Action;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Events {

    public static void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(
                !Validator.can(
                        event.getPlayer(),
                        List.of(Action.BREAK, Action.USE_ENCHANTMENT),
                        event
                )
        );
    }

    public static void onInteractionEvent(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (item == null || item.getType().isAir()) {
            return;
        }

        // Right-click armor equipping
        if (isArmor(item)
                && !Validator.can(event.getPlayer(), Action.EQUIP, event)) {
            event.setCancelled(true);
            return;
        }

        // Normal item interaction
        event.setCancelled(
                !Validator.can(event.getPlayer(), Action.INTERACT, event)
        );
    }

    public static void onBlockPlaceEvent(BlockPlaceEvent event) {
        event.setCancelled(
                !Validator.can(event.getPlayer(), Action.PLACE, event)
        );
    }

    public static void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            event.setCancelled(
                    !Validator.can(
                            player,
                            List.of(Action.HIT, Action.USE_ENCHANTMENT),
                            event
                    )
            );
        }
    }

    public static void onPlayerMoveEvent(PlayerMoveEvent event) {
        if (event.getPlayer().isGliding()
                && !Validator.can(
                event.getPlayer(),
                List.of(Action.GLYDE),
                event
        )) {
            event.getPlayer().setGliding(false);
        }
    }

    public static void onInventoryClickEvent(InventoryClickEvent event) {
        if (!isArmorEquipAttempt(event)) {
            return;
        }

        HumanEntity entity = event.getWhoClicked();

        if (!Validator.can(entity, Action.EQUIP, event)) {
            event.setCancelled(true);
        }
    }

    public static void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        event.setCancelled(!Validator.can(event.getEntity(), Action.SPAWN, event));
    }

    private static boolean isArmorEquipAttempt(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            return true;
        }

        if (event.isShiftClick()) {
            return isArmor(event.getCurrentItem());
        }

        if (event.getClick() == ClickType.NUMBER_KEY
                && event.getSlotType() == InventoryType.SlotType.ARMOR
                && event.getWhoClicked() instanceof Player player) {
            return isArmor(
                    player.getInventory().getItem(event.getHotbarButton())
            );
        }

        return false;
    }

    private static boolean isArmor(ItemStack item) {
        if (item == null || item.getType().isAir()) {
            return false;
        }

        Material type = item.getType();
        String name = type.name();

        return name.endsWith("_HELMET")
                || name.endsWith("_CHESTPLATE")
                || name.endsWith("_LEGGINGS")
                || name.endsWith("_BOOTS")
                || type == Material.ELYTRA;
    }
}