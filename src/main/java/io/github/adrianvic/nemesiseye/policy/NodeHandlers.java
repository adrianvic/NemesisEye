package io.github.adrianvic.nemesiseye.policy;

import io.github.adrianvic.nemesiseye.policy.handlers.bePlaced;
import io.github.adrianvic.nemesiseye.policy.handlers.useEnchantment;
import io.github.adrianvic.nemesiseye.policy.handlers.useItem;

import java.util.HashMap;
import java.util.Map;

public class NodeHandlers {
    private static final Map<Action, NodeHandler> handlers = new HashMap<>();
    
    static {
        handlers.put(Action.HIT, new useItem());
        handlers.put(Action.PLACE, new bePlaced());
        handlers.put(Action.INTERACT, new useItem());
        handlers.put(Action.USE_ENCHANTMENT, new useEnchantment());
    }
    
    public static NodeHandler get(Action type) {
        return handlers.get(type);
    }
}
