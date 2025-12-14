package io.github.adrianvic.nemesiseye.impl;

import io.github.adrianvic.nemesiseye.Events;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

public class EntityEventListener extends EntityListener {
    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent e) Events.onEntityDamageByEntityEvent(e);
    }
}