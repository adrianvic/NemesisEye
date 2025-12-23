package io.github.adrianvic.nemesiseye.policy.parser;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.*;
import io.github.adrianvic.nemesiseye.policy.policies.Core;
import io.github.adrianvic.nemesiseye.policy.policies.LocationPolicy;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationPolicyParser implements PolicyParser {
    private Glimmer glim = Nemesis.getInstance().getGlimmer();

    public Policy parse(Core corePolicy, Map<?, ?> raw) {
        List<Glimmer.Box> locations = DataShifter.configLocationParser(raw.get("locations"));
        return new LocationPolicy(corePolicy.name(), locations, corePolicy.nodes(), corePolicy.nodeAllowlist(), corePolicy.policyAllowList(), corePolicy.effect(), corePolicy.weight());
    }
}
