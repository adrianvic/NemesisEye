package io.github.adrianvic.nemesiseye.impl.events;

import io.github.adrianvic.nemesiseye.Events;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class PlayerEventListener extends PlayerListener {
    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        Events.onInteractionEvent(event);
    }
}

