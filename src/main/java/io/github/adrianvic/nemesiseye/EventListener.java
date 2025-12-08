package io.github.adrianvic.nemesiseye;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(!Validator.canBreak(event.getPlayer()));
    }

    @EventHandler
    public void onInteractionEvent(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            event.setCancelled(!Validator.canInteract(event.getPlayer()));
        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            event.setCancelled(!Validator.canHit((HumanEntity) event.getDamager()));
        }
    }
}
