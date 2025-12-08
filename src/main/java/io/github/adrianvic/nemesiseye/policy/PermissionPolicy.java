package io.github.adrianvic.nemesiseye.policy;

import org.bukkit.permissions.Permission;

import java.util.ArrayList;

public record PermissionPolicy(String name, ArrayList<Permission> permissions, PolicyNode nodes, boolean allowlist) {}
