package io.github.adrianvic.nemesiseye.policy.parser;

import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyParser;
import io.github.adrianvic.nemesiseye.policy.policies.Core;
import io.github.adrianvic.nemesiseye.policy.policies.GlobalPolicy;

import java.util.Map;

public class GlobalPolicyParser implements PolicyParser {
    @Override
    public Policy parse(Core corePolicy, Map<?, ?> raw) {
        return new GlobalPolicy(corePolicy.name(), corePolicy.nodes(), corePolicy.policyAllowList(), corePolicy.effect(), corePolicy.weight());
    }
}
