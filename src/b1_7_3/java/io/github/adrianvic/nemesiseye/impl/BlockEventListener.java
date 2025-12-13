package io.github.adrianvic.nemesiseye.impl;

import io.github.adrianvic.nemesiseye.Events;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class BlockEventListener extends BlockListener {
    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        Events.onBlockBreak(event);
    }

}
