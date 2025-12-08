package io.github.adrianvic.nemesiseye.policy;

import io.github.adrianvic.nemesiseye.policy.handlers.attackWith;
import io.github.adrianvic.nemesiseye.policy.handlers.useEnchantment;
import io.github.adrianvic.nemesiseye.policy.handlers.useItem;

import java.util.HashMap;
import java.util.Map;

public class NodeHandlers {
    private static final Map<String, NodeHandler> handlers = new HashMap<>();
    
    static {
        handlers.put("attackWithItemInHand", new attackWith());
        handlers.put("useItem", new useItem());
        handlers.put("useEnchantment", new useEnchantment());
    }
    
    public static NodeHandler get(String type) {
        return handlers.get(type);
    }
}
