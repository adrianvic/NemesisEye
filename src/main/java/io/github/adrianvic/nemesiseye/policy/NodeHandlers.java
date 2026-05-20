package io.github.adrianvic.nemesiseye.policy;

import io.github.adrianvic.nemesiseye.policy.handlers.*;

import java.util.HashMap;
import java.util.Map;

public class NodeHandlers {
    private static final Map<Action, NodeHandler> handlers = new HashMap<>();
    
    static {
        handlers.put(Action.HIT, new UseItem());
        handlers.put(Action.PLACE, new BePlaced());
        handlers.put(Action.INTERACT, new UseItem());
        handlers.put(Action.USE_ENCHANTMENT, new UseEnchantment());
        handlers.put(Action.GLYDE, new Glyde());
        handlers.put(Action.EQUIP, new Equip());
        handlers.put(Action.SPAWN, new Spawn());
        handlers.put(Action.BREAK, new UseItem()); // TODO: implement place handler
    }
    
    public static NodeHandler get(Action type) {
        return handlers.get(type);
    }
}
