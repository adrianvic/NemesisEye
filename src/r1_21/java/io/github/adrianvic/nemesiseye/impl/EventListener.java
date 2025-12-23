package io.github.adrianvic.nemesiseye.impl;

import io.github.adrianvic.nemesiseye.Events;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Events.onBlockBreak(event);
    }

    @EventHandler
    public void onInteractionEvent(PlayerInteractEvent event) {
        Events.onInteractionEvent(event);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        Events.onEntityDamageByEntityEvent(event);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Events.onBlockPlaceEvent(event);
    }
}