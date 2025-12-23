package io.github.adrianvic.nemesiseye.policy.parser;

import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.policy.PolicyParser;
import io.github.adrianvic.nemesiseye.policy.policies.Core;
import io.github.adrianvic.nemesiseye.policy.policies.PermissionPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PermissionPolicyParser implements PolicyParser {
    @Override
    public Policy parse(Core corePolicy, Map<?, ?> raw) {
        Object rawPerms = raw.get("permissions");
        List<String> permissions = new ArrayList<>();

        if (rawPerms instanceof List<?> list) {
            for (Object o : list) {
                if (o instanceof String s) {
                    permissions.add(s);
                }
            }
        }

        return new PermissionPolicy(corePolicy.name(), permissions, corePolicy.nodes(), corePolicy.nodeAllowlist(), corePolicy.policyAllowList(), corePolicy.effect(), corePolicy.weight());
    }
}
