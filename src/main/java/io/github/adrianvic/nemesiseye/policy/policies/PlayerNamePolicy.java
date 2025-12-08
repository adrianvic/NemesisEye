package io.github.adrianvic.nemesiseye.policy.policies;

import io.github.adrianvic.nemesiseye.policy.PolicyNode;

import java.util.ArrayList;

public record PlayerNamePolicy(String name, ArrayList<String> playerName, PolicyNode nodes, boolean allowlist) {}