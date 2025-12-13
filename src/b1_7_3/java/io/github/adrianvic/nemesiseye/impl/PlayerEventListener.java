package io.github.adrianvic.nemesiseye.impl;

import io.github.adrianvic.nemesiseye.Events;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class PlayerEventListener extends PlayerListener {
    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        Events.onInteractionEvent(event);
    }
}

