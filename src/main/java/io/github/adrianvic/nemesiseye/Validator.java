package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.Policy;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    public static boolean can(HumanEntity entity, List<Action> actions, Event event) {
        for (Action action : actions) {
            System.out.println(action);
            if (!can(entity, action, event)) {
                return false;
            }
        }
        return true;
    }

    public static boolean can(HumanEntity entity, Action action, Event event) {
        boolean restricted = false;
        boolean allowed = false;

        for (Policy policy : getPoliciesForEntity(entity)) {

            boolean matches = policy.matches(entity, action, event);

            switch (policy.effect()) {
                case ALLOW:
                    if (matches) return true;
                    break;

                case DENY:
                    if (matches) return false;
                    break;
            }
        }

        if (restricted) {
            return allowed;
        }

        return true;
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
