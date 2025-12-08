package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.policies.LocationPolicy;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    public static boolean canInteract(HumanEntity entity) {
        return checkAgainstEntity(entity, Action.INTERACT);
    }
    public static boolean canBreak(HumanEntity entity) {
        return checkAgainstEntity(entity, Action.BREAK);
    }

    public static boolean canHit(HumanEntity entity) {
        return checkAgainstEntity(entity, Action.HIT);
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
        boolean allowed = node.getHandler().allows(entity, node, action);
        return node.isWhitelist() != allowed;
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
        List<Policy> applyingLPS = new ArrayList<>();
        for (Policy p : ps) {
            if (p instanceof LocationPolicy lp) {
                for (ArrayList<BoundingBox> boxes : lp.locations()) {
                    for (BoundingBox box : boxes) {
                        if (box.contains(entity.getLocation().toVector())) {
                            applyingLPS.add(lp);
                        }
                    }
                }
            }
        }
        return applyingLPS;
    }
}
