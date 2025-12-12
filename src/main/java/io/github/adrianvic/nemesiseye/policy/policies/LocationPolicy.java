package io.github.adrianvic.nemesiseye.policy.policies;

import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;

import java.util.ArrayList;
import java.util.List;

public record LocationPolicy(String name, List<ArrayList<Glimmer.Box>> locations, List<PolicyNode> nodes, boolean allowlist) implements Policy {}