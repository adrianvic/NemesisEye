package io.github.adrianvic.nemesiseye.policy.parser;

import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyParser;
import io.github.adrianvic.nemesiseye.policy.policies.Core;
import io.github.adrianvic.nemesiseye.policy.policies.PlayerNamePolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerNamePolicyParser implements PolicyParser {
    @Override
    public Policy parse(Core corePolicy, Map<?, ?> raw) {
        Object rawNames = raw.get("names");
        List<String> names = new ArrayList<>();

        if (rawNames instanceof List<?> list) {
            for (Object o : list) {
                if (o instanceof String s) {
                    names.add(s);
                }
            }
        }

        return new PlayerNamePolicy(corePolicy.name(), names, corePolicy.nodes(), corePolicy.effect(), corePolicy.policyAllowList(), corePolicy.weight());
    }
}
