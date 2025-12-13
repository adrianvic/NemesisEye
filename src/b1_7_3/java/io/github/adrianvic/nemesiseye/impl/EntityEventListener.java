package io.github.adrianvic.nemesiseye.impl;

import io.github.adrianvic.nemesiseye.Events;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityListener;

public class EntityEventListener extends EntityListener {
    public void onEntityDamage(EntityDamageByEntityEvent event) {
//        Events.onEntityDamageByEntityEvent(event);
        event.setCancelled(true);
    }
}

