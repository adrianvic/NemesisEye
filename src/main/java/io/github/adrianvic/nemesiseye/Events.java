package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.policy.Action;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Events {
    public static void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(!Validator.can(event.getPlayer(), Action.BREAK));
    }

    public static void onInteractionEvent(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            event.setCancelled(!Validator.can(event.getPlayer(), Action.INTERACT));
        }
    }

    public static void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            event.setCancelled(!Validator.can((HumanEntity) event.getDamager(), Action.HIT));
        }
    }
}
