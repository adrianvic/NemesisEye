package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.NodeHandler;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    private final static Glimmer glim = Nemesis.getInstance().getGlimmer();

    public static boolean can(HumanEntity entity, Action action) {
        return checkAgainstEntity(entity, action);
    }

    public static boolean checkAgainstEntity(HumanEntity entity, Action action) {
        return checkAgainstNodes(entity, getNodesForPolicies(getPoliciesForEntity(entity)), action);
    }

    public static boolean checkAgainstNodes(HumanEntity entity, List<PolicyNode> nodes, Action action) {
        for (PolicyNode n : nodes) {
            if (!checkAgainstNode(entity, n, action)) return false;
        }
        return true;
    }

    public static boolean checkAgainstNode(HumanEntity entity, PolicyNode node, Action action) {
        for (NodeHandler handler : node.getHandler()) {
            if (!handler.allows(entity, node, action)) {
                return false;
            }
        }
        return true;
    }

    public static List<PolicyNode> getNodesForPolicies(List<Policy> policies) {
        List<PolicyNode> nodes = new ArrayList<>();
        for (Policy p : policies) {
            nodes.addAll(p.nodes());
        }
        return nodes;
    }

    public static List<Policy> getPoliciesForEntity(HumanEntity entity) {
        List<Policy> ps = Config.getInstance().getPolicies();
        List<Policy> result = new ArrayList<>();
        for (Policy policy : ps) {
            if (policy.applies(entity)) result.add(policy);
        }
        return result;
    }
}
