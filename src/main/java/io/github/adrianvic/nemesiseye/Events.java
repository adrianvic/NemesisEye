package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Events {

    private static Glimmer g() {
        return Nemesis.getInstance().getGlimmer();
    }

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

        if (g().isAir(item)) {
            return;
        }

        // Right-click armor equipping
        if (g().isArmor(item)
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
        if (g().isGliding(event.getPlayer())
                && !Validator.can(
                event.getPlayer(),
                List.of(Action.GLYDE),
                event
        )) {
            g().setGliding(event.getPlayer(), false);
        }
    }

    public static void onInventoryClickEvent(InventoryClickEvent event) {
        if (!g().isArmorEquipAttempt(event)) {
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
}