package io.github.adrianvic.nemesiseye.impl;

import io.github.adrianvic.nemesiseye.Events;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

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

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) { Events.onPlayerMoveEvent(event); }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClickEvent(InventoryClickEvent event) { Events.onInventoryClickEvent(event); }

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) { Events.onCreatureSpawnEvent(event); }
}