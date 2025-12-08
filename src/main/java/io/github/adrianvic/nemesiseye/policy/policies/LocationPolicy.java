package io.github.adrianvic.nemesiseye.policy.policies;

import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public record LocationPolicy(String name, List<ArrayList<BoundingBox>> locations, List<PolicyNode> nodes, boolean allowlist) implements Policy {}