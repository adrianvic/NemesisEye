package io.github.adrianvic.nemesiseye.policy;

import io.github.adrianvic.nemesiseye.Config;
import io.github.adrianvic.nemesiseye.DataShifter;
import org.apache.logging.log4j.status.StatusLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record PolicyNode(List<Action> actions, List<Object> values, Effect effect) {
    public static List<PolicyNode> parseNodes(List<Map<Object,Object>> raw, Effect effect) {
        List<PolicyNode> nodes = new ArrayList<>();

        for (Map<Object, Object> m : raw) {
            for (Map.Entry<Object, Object> rawNode : m.entrySet()) {
                List<Action> nodeActions = new ArrayList<>();
                List<Object> nodeValues = new ArrayList<>();
                Effect nodeEffect = effect;

                if (rawNode.getKey() instanceof List<?> rawTypes && rawNode.getValue() instanceof Map<?,?> rawNodeValues) {
                    for (Object rawType : rawTypes) {
                        if (rawType instanceof String rts && !rts.isEmpty() && !(rts == null) && DataShifter.enumOrDefault(Action.class, rts, null) != null) {
                            nodeActions.add(DataShifter.enumOrDefault(Action.class, rts, null));
                        }
                    }
                    Map<String, Object> semiParsedNodeValue = new HashMap<>();
                    for (Map.Entry<?,?> rawNodeValueEntries : rawNodeValues.entrySet()) {
                        if (rawNodeValueEntries.getKey() instanceof String stringKey) {
                            semiParsedNodeValue.put(stringKey, rawNodeValueEntries.getValue());
                        }
                    }

                    if (semiParsedNodeValue.get("values") instanceof List<?> l) {
                        for (Object v : l) {
                            nodeValues.add(v);
                        }
                    }

                    if (semiParsedNodeValue.get("effect") instanceof String efc) {
                        Effect type = DataShifter.enumOrDefault(Effect.class, efc, null);
                        if (type != null) {
                            nodeEffect = type;
                        }
                    }
                }

                nodes.add(new PolicyNode(nodeActions, nodeValues, nodeEffect));
            }
        }
        return nodes;
    }

    public List<NodeHandler> getHandler() {
        List<NodeHandler> handlers = new ArrayList<>();
        for (Action a : actions) {
            handlers.add(NodeHandlers.get(a));
        }
        return handlers;
    }
}