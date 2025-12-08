package io.github.adrianvic.nemesiseye.policy;

import java.util.ArrayList;

public record PlayerNamePolicy(String name, ArrayList<String> playerName, PolicyNode nodes, boolean allowlist) {}