package io.github.adrianvic.nemesiseye.policy.policies;

import io.github.adrianvic.nemesiseye.policy.PolicyNode;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;

public record PermissionPolicy(String name, ArrayList<Permission> permissions, PolicyNode nodes, boolean allowlist) {}
