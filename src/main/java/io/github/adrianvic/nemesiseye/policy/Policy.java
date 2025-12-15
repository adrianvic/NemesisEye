package io.github.adrianvic.nemesiseye.policy;

import org.bukkit.entity.HumanEntity;

import java.util.List;

public interface Policy {
    String name();
    List<PolicyNode> nodes();
    boolean allowlist();
    boolean applies(HumanEntity entity);

}
